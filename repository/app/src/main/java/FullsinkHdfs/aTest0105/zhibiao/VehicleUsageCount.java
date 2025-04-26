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
public class VehicleUsageCount {
    public static void main(String[] args) throws Exception {

        // 设置 Flink 环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 创建并注册 logistics_vehicle_summary 表
        String createLogisticsVehicleSummaryTable = "CREATE TABLE logistics_vehicle_summary (" +
                "logistics_id BIGINT, " +
                "logistics_number STRING, " +
                "order_id BIGINT, " +
                "logistics_status STRING, " +
                "vehicle_id BIGINT, " +
                "license_plate STRING, " +
                "model STRING, " +
                "vehicle_status STRING, " +
                "PRIMARY KEY (logistics_id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'jdbc', " +
                "'url' = 'jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai', " +
                "'table-name' = 'logistics_vehicle_summary', " +
                "'driver' = 'com.mysql.cj.jdbc.Driver', " +
                "'username' = 'root', " +
                "'password' = '1234'" +
                ")";
        tableEnv.executeSql(createLogisticsVehicleSummaryTable);

        // SQL 查询：统计每个车牌号的使用次数
        String query = "SELECT " +
                "license_plate, " +
                "COUNT(*) AS usage_count " +
                "FROM logistics_vehicle_summary " +
                "GROUP BY license_plate";

        // 执行 SQL 查询
        Table resultTable = tableEnv.sqlQuery(query);

        // 将查询结果转为 DataStream
        DataStream<Row> changelogStream = tableEnv.toChangelogStream(resultTable);
        changelogStream.print();

        // 映射到自定义 POJO
        SingleOutputStreamOperator<VehicleUsageSummary> resultStream = changelogStream.map(new MapFunction<Row, VehicleUsageSummary>() {
            @Override
            public VehicleUsageSummary map(Row row) throws Exception {
                return new VehicleUsageSummary(
                        (String) row.getField(0),  // license_plate
                        ((Long) row.getField(1)).intValue()  // usage_count 转换为 Integer
                );
            }
        });

        // 定义 JDBC 输出 Sink
        String insertQuery = "INSERT INTO vehicle_usage_count " +
                "(license_plate, usage_count) " +
                "VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "usage_count = VALUES(usage_count)";

        resultStream.addSink(JdbcSink.sink(
                insertQuery,
                new JdbcStatementBuilder<VehicleUsageSummary>() {
                    @Override
                    public void accept(PreparedStatement ps, VehicleUsageSummary summary) throws SQLException {
                        ps.setString(1, summary.getLicensePlate());
                        ps.setInt(2, summary.getUsageCount());
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
        env.execute("Flink Vehicle Usage Count Example");
    }

    // 自定义 POJO 用于映射结果
    public static class VehicleUsageSummary {
        private String licensePlate;
        private int usageCount;

        public VehicleUsageSummary() {
        }

        public VehicleUsageSummary(String licensePlate, int usageCount) {
            this.licensePlate = licensePlate;
            this.usageCount = usageCount;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public int getUsageCount() {
            return usageCount;
        }

        public void setUsageCount(int usageCount) {
            this.usageCount = usageCount;
        }
    }
}
