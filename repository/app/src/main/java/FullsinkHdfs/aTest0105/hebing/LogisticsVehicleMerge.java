package FullsinkHdfs.aTest0105.hebing;

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

public class LogisticsVehicleMerge {
    public static void main(String[] args) throws Exception {

        // 设置 Flink 环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 创建并注册 logistics 表
        String createLogisticsTable = "CREATE TABLE logistics (" +
                "id BIGINT, " +
                "logistics_number STRING, " +
                "order_id BIGINT, " +
                "vehicle_id BIGINT, " +
                "employee_id BIGINT, " +
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

        // 创建并注册 vehicle 表
        String createVehicleTable = "CREATE TABLE vehicle (" +
                "id BIGINT, " +
                "license_plate STRING, " +
                "model STRING, " +
                "status STRING, " +
                "PRIMARY KEY (id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'jdbc', " +
                "'url' = 'jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai', " +
                "'table-name' = 'vehicle', " +
                "'driver' = 'com.mysql.cj.jdbc.Driver', " +
                "'username' = 'root', " +
                "'password' = '1234'" +
                ")";
        tableEnv.executeSql(createVehicleTable);

        // SQL 查询：关联 logistics 和 vehicle 表，输出合并数据
        String query = "SELECT " +
                "l.id AS logistics_id, " +
                "l.logistics_number, " +
                "l.order_id, " +
                "l.status AS logistics_status, " +
                "v.id AS vehicle_id, " +
                "v.license_plate, " +
                "v.model, " +
                "v.status AS vehicle_status " +
                "FROM logistics l " +
                "JOIN vehicle v " +
                "ON l.vehicle_id = v.id";

        // 执行 SQL 查询
        Table resultTable = tableEnv.sqlQuery(query);

        // 将查询结果转为 DataStream
        DataStream<Row> changelogStream = tableEnv.toChangelogStream(resultTable);
        changelogStream.print();

        // 映射到自定义 POJO
        SingleOutputStreamOperator<LogisticsVehicleSummary> resultStream = changelogStream.map(new MapFunction<Row, LogisticsVehicleSummary>() {
            @Override
            public LogisticsVehicleSummary map(Row row) throws Exception {
                return new LogisticsVehicleSummary(
                        (Long) row.getField(0),  // logistics_id
                        (String) row.getField(1),  // logistics_number
                        (Long) row.getField(2),  // order_id
                        (String) row.getField(3),  // logistics_status
                        (Long) row.getField(4),  // vehicle_id
                        (String) row.getField(5),  // license_plate
                        (String) row.getField(6),  // model
                        (String) row.getField(7)   // vehicle_status
                );
            }
        });

        // 定义 JDBC 输出 Sink
        String insertQuery = "INSERT INTO logistics_vehicle_summary " +
                "(logistics_id, logistics_number, order_id, vehicle_id, license_plate, model, vehicle_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "logistics_number = VALUES(logistics_number), " +
                "order_id = VALUES(order_id), " +
                "license_plate = VALUES(license_plate), " +
                "model = VALUES(model), " +
                "vehicle_status = VALUES(vehicle_status)";


        resultStream.addSink(JdbcSink.sink(
                insertQuery,
                new JdbcStatementBuilder<LogisticsVehicleSummary>() {
                    @Override
                    public void accept(PreparedStatement ps, LogisticsVehicleSummary summary) throws SQLException {
                        ps.setLong(1, summary.getLogisticsId());
                        ps.setString(2, summary.getLogisticsNumber());
                        ps.setLong(3, summary.getOrderId());
                        ps.setLong(4, summary.getVehicleId());
                        ps.setString(5, summary.getLicensePlate());
                        ps.setString(6, summary.getModel());
                        ps.setString(7, summary.getVehicleStatus());
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
        env.execute("Flink Logistics Vehicle Merge Example");
    }

    // 自定义 POJO 用于映射结果
    public static class LogisticsVehicleSummary {
        private Long logisticsId;
        private String logisticsNumber;
        private Long orderId;
        private String logisticsStatus;
        private Long vehicleId;
        private String licensePlate;
        private String model;
        private String vehicleStatus;

        public LogisticsVehicleSummary() {
        }

        public LogisticsVehicleSummary(Long logisticsId, String logisticsNumber, Long orderId, String logisticsStatus,
                                       Long vehicleId, String licensePlate, String model, String vehicleStatus) {
            this.logisticsId = logisticsId;
            this.logisticsNumber = logisticsNumber;
            this.orderId = orderId;
            this.logisticsStatus = logisticsStatus;
            this.vehicleId = vehicleId;
            this.licensePlate = licensePlate;
            this.model = model;
            this.vehicleStatus = vehicleStatus;
        }

        // Getter 和 Setter 方法
        public Long getLogisticsId() {
            return logisticsId;
        }

        public void setLogisticsId(Long logisticsId) {
            this.logisticsId = logisticsId;
        }

        public String getLogisticsNumber() {
            return logisticsNumber;
        }

        public void setLogisticsNumber(String logisticsNumber) {
            this.logisticsNumber = logisticsNumber;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getLogisticsStatus() {
            return logisticsStatus;
        }

        public void setLogisticsStatus(String logisticsStatus) {
            this.logisticsStatus = logisticsStatus;
        }

        public Long getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Long vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getVehicleStatus() {
            return vehicleStatus;
        }

        public void setVehicleStatus(String vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
        }
    }
}