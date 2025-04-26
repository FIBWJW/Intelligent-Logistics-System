package FullsinkHdfs.aTest0105.zhibiao;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.sql.PreparedStatement;
import java.sql.SQLException;
public class LogisticsStatusStatistics {
    public static void main(String[] args) throws Exception {

        // 设置 Flink 环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 创建并注册 logistics 表
        String createLogisticsTable = "CREATE TABLE logistics (" +
                "id INT, " +
                "logistics_number STRING, " +
                "order_id INT, " +
                "vehicle_id INT, " +
                "employee_id INT, " +
                "recipient INT, " +
                "status STRING, " +
                "phone STRING, " +
                "remark STRING, " +
                "PRIMARY KEY (id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'jdbc', " +
                "'url' = 'jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai', " +
                "'table-name' = 'logistics', " +
                "'driver' = 'com.mysql.cj.jdbc.Driver', " +
                "'username' = 'root', " +
                "'password' = '1234'" +
                ")";
        tableEnv.executeSql(createLogisticsTable);

        // SQL 查询：统计每种 status 的数量
        String query = "SELECT " +
                "status, " +
                "COUNT(*) AS status_count " +
                "FROM logistics " +
                "GROUP BY status";

        // 执行 SQL 查询
        Table resultTable = tableEnv.sqlQuery(query);

        // 将查询结果转为 DataStream
        DataStream<Row> changelogStream = tableEnv.toChangelogStream(resultTable);
        changelogStream.print();

        // 映射到自定义 POJO
        SingleOutputStreamOperator<StatusSummary> resultStream = changelogStream.map(new MapFunction<Row, StatusSummary>() {
            @Override
            public StatusSummary map(Row row) throws Exception {
                return new StatusSummary((String) row.getField(0), ((Long) row.getField(1)).intValue());
            }
        });

        // 定义 JDBC 输出 Sink
        String insertQuery = "INSERT INTO logistics_status_summary (status, status_count) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE status_count = ?";

        resultStream.addSink(JdbcSink.sink(
                insertQuery,
                new JdbcStatementBuilder<StatusSummary>() {
                    @Override
                    public void accept(PreparedStatement ps, StatusSummary statusSummary) throws SQLException {
                        ps.setString(1, statusSummary.getStatus());
                        ps.setInt(2, statusSummary.getStatusCount());
                        ps.setInt(3, statusSummary.getStatusCount());
                    }
                },
                JdbcExecutionOptions.builder()
                        .withBatchSize(10)
                        .withBatchIntervalMs(200)
                        .withMaxRetries(5)
                        .build(),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai")
                        .withDriverName("com.mysql.cj.jdbc.Driver")
                        .withUsername("root")
                        .withPassword("1234")
                        .build()
        ));

        // 执行流处理
        env.execute("Flink Logistics Status Statistics Example");
    }

    // 自定义 POJO 用于映射结果
    public static class StatusSummary {
        private String status;
        private int statusCount;

        public StatusSummary() {
        }

        public StatusSummary(String status, int statusCount) {
            this.status = status;
            this.statusCount = statusCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getStatusCount() {
            return statusCount;
        }

        public void setStatusCount(int statusCount) {
            this.statusCount = statusCount;
        }
    }
}
