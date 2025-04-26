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
import org.apache.hadoop.security.UserGroupInformation;
import readsinkTest.FlinkHdfsSinkFunction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
public class OrderStatistics {
    public static void main(String[] args) throws Exception {



        // 设置 Flink 环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 创建并注册订单表
        String createOrdersTable = "CREATE TABLE orders (" +
                "id INT, " +
                "order_number STRING, " +
                "recipient_name STRING, " +
                "recipient_phone STRING, " +
                "address STRING, " +
                "created_at TIMESTAMP(3), " +
                "status STRING, " +
                "PRIMARY KEY (id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'jdbc', " +
                "'url' = 'jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai', " +
                "'table-name' = 'orders', " +
                "'driver' = 'com.mysql.cj.jdbc.Driver', " +
                "'username' = 'root', " +
                "'password' = '1234'" +
                ")";
        tableEnv.executeSql(createOrdersTable);

        // SQL 查询：统计每个省份的订单数量
        String query = "SELECT " +
                "address AS province, " +
                "COUNT(*) AS order_count " +
                "FROM orders " +
                "GROUP BY address";

        // 执行 SQL 查询
        Table resultTable = tableEnv.sqlQuery(query);

        // 将查询结果转为 DataStream
        DataStream<Row> changelogStream = tableEnv.toChangelogStream(resultTable);
        changelogStream.print();

        // 映射到自定义 POJO
        SingleOutputStreamOperator<OrderSummary> resultStream = changelogStream.map(new MapFunction<Row, OrderSummary>() {
            @Override
            public OrderSummary map(Row row) throws Exception {
                return new OrderSummary((String) row.getField(0), ((Long) row.getField(1)).intValue());
            }
        });

        // 定义 JDBC 输出 Sink
        String insertQuery = "INSERT INTO province_order_summary (province, order_count) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE order_count = ?";

        resultStream.addSink(JdbcSink.sink(
                insertQuery,
                new JdbcStatementBuilder<OrderSummary>() {
                    @Override
                    public void accept(PreparedStatement ps, OrderSummary orderSummary) throws SQLException {
                        ps.setString(1, orderSummary.getProvince());
                        ps.setInt(2, orderSummary.getOrderCount());
                        ps.setInt(3, orderSummary.getOrderCount());
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
        env.execute("Flink Order Statistics Example");
    }

    // 自定义 POJO 用于映射结果
    public static class OrderSummary {
        private String province;
        private int orderCount;

        public OrderSummary() {
        }

        public OrderSummary(String province, int orderCount) {
            this.province = province;
            this.orderCount = orderCount;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }
    }
}