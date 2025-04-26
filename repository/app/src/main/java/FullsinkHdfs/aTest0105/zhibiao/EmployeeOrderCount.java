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

public class EmployeeOrderCount {
    public static void main(String[] args) throws Exception {

        // 设置 Flink 环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 创建并注册 logistics_employee_summary 表
        String createLogisticsEmployeeSummaryTable = "CREATE TABLE logistics_employee_summary (" +
                "logistics_id BIGINT, " +
                "logistics_number STRING, " +
                "order_id BIGINT, " +
                "status STRING, " +
                "employee_id BIGINT, " +
                "employee_name STRING, " +
                "gender STRING, " +
                "contact_info STRING, " +
                "`position` STRING, " +
                "PRIMARY KEY (logistics_id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'jdbc', " +
                "'url' = 'jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai', " +
                "'table-name' = 'logistics_employee_summary', " +
                "'driver' = 'com.mysql.cj.jdbc.Driver', " +
                "'username' = 'root', " +
                "'password' = '1234'" +
                ")";
        tableEnv.executeSql(createLogisticsEmployeeSummaryTable);

        // SQL 查询：统计每个员工的订单数
        String query = "SELECT " +
                "employee_name, " +
                "COUNT(*) AS order_count " +
                "FROM logistics_employee_summary " +
                "GROUP BY employee_name";

        // 执行 SQL 查询
        Table resultTable = tableEnv.sqlQuery(query);

        // 将查询结果转为 DataStream
        DataStream<Row> changelogStream = tableEnv.toChangelogStream(resultTable);
        changelogStream.print();

        // 映射到自定义 POJO
        SingleOutputStreamOperator<EmployeeOrderSummary> resultStream = changelogStream.map(new MapFunction<Row, EmployeeOrderSummary>() {
            @Override
            public EmployeeOrderSummary map(Row row) throws Exception {
                return new EmployeeOrderSummary(
                        (String) row.getField(0),  // employee_name
                        ((Long) row.getField(1)).intValue()  // order_count 转换为 Integer
                );
            }
        });

        // 定义 JDBC 输出 Sink
        String insertQuery = "INSERT INTO employee_order_count " +
                "(employee_name, order_count) " +
                "VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "order_count = VALUES(order_count)";

        resultStream.addSink(JdbcSink.sink(
                insertQuery,
                new JdbcStatementBuilder<EmployeeOrderSummary>() {
                    @Override
                    public void accept(PreparedStatement ps, EmployeeOrderSummary summary) throws SQLException {
                        ps.setString(1, summary.getEmployeeName());
                        ps.setInt(2, summary.getOrderCount());
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
        env.execute("Flink Employee Order Count Example");
    }

    // 自定义 POJO 用于映射结果
    public static class EmployeeOrderSummary {
        private String employeeName;
        private int orderCount;

        public EmployeeOrderSummary() {
        }

        public EmployeeOrderSummary(String employeeName, int orderCount) {
            this.employeeName = employeeName;
            this.orderCount = orderCount;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }
    }
}
