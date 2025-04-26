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

public class LogisticsEmployeeMerge {
    public static void main(String[] args) throws Exception {

        // 设置 Flink 环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 创建并注册 logistics 表
        String createLogisticsTable = "CREATE TABLE logistics (" +
                "id BIGINT, " +  // 修改为 BIGINT
                "logistics_number STRING, " +
                "order_id BIGINT, " +  // 修改为 BIGINT
                "vehicle_id INT, " +
                "employee_id BIGINT, " +  // 修改为 BIGINT
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

        // 创建并注册 employee 表
        String createEmployeeTable = "CREATE TABLE employee (" +
                "id BIGINT, " +  // 修改为 BIGINT
                "name STRING, " +
                "gender STRING, " +
                "contact_info STRING, " +
                "`position` STRING, " +  // position 是 SQL 保留关键字，用反引号包裹
                "PRIMARY KEY (id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'jdbc', " +
                "'url' = 'jdbc:mysql://localhost:3306/lyzTestzhu?serverTimezone=Asia/Shanghai', " +
                "'table-name' = 'employee', " +
                "'driver' = 'com.mysql.cj.jdbc.Driver', " +
                "'username' = 'root', " +
                "'password' = '1234'" +
                ")";
        tableEnv.executeSql(createEmployeeTable);

        // SQL 查询：关联 logistics 和 employee 表，输出合并数据
        String query = "SELECT " +
                "l.id AS logistics_id, " +
                "l.logistics_number, " +
                "l.order_id, " +
                "l.status, " +
                "e.id AS employee_id, " +
                "e.name AS employee_name, " +
                "e.gender, " +
                "e.contact_info, " +
                "e.`position` " +  // position 用反引号包裹
                "FROM logistics l " +
                "JOIN employee e " +
                "ON l.employee_id = e.id";

        // 执行 SQL 查询
        Table resultTable = tableEnv.sqlQuery(query);

        // 将查询结果转为 DataStream
        DataStream<Row> changelogStream = tableEnv.toChangelogStream(resultTable);
        changelogStream.print();

        // 映射到自定义 POJO
        SingleOutputStreamOperator<LogisticsEmployeeSummary> resultStream = changelogStream.map(new MapFunction<Row, LogisticsEmployeeSummary>() {
            @Override
            public LogisticsEmployeeSummary map(Row row) throws Exception {
                return new LogisticsEmployeeSummary(
                        (Long) row.getField(0),  // logistics_id
                        (String) row.getField(1),  // logistics_number
                        (Long) row.getField(2),  // order_id
                        (String) row.getField(3),  // status
                        (Long) row.getField(4),  // employee_id
                        (String) row.getField(5),  // employee_name
                        (String) row.getField(6),  // gender
                        (String) row.getField(7),  // contact_info
                        (String) row.getField(8)   // position
                );
            }
        });

        // 定义 JDBC 输出 Sink
        String insertQuery = "INSERT INTO logistics_employee_summary " +
                "(logistics_id, logistics_number, order_id, status, employee_id, employee_name, gender, contact_info, position) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "logistics_number = VALUES(logistics_number), " +
                "order_id = VALUES(order_id), " +
                "status = VALUES(status), " +
                "employee_name = VALUES(employee_name), " +
                "gender = VALUES(gender), " +
                "contact_info = VALUES(contact_info), " +
                "position = VALUES(position)";

        resultStream.addSink(JdbcSink.sink(
                insertQuery,
                new JdbcStatementBuilder<LogisticsEmployeeSummary>() {
                    @Override
                    public void accept(PreparedStatement ps, LogisticsEmployeeSummary summary) throws SQLException {
                        ps.setLong(1, summary.getLogisticsId());  // logistics_id
                        ps.setString(2, summary.getLogisticsNumber());  // logistics_number
                        ps.setLong(3, summary.getOrderId());  // order_id
                        ps.setString(4, summary.getStatus());  // status
                        ps.setLong(5, summary.getEmployeeId());  // employee_id
                        ps.setString(6, summary.getEmployeeName());  // employee_name
                        ps.setString(7, summary.getGender());  // gender
                        ps.setString(8, summary.getContactInfo());  // contact_info
                        ps.setString(9, summary.getPosition());  // position
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
        env.execute("Flink Logistics Employee Merge Example");
    }

    // 自定义 POJO 用于映射结果
    public static class LogisticsEmployeeSummary {
        private Long logisticsId;  // 使用 Long 类型
        private String logisticsNumber;
        private Long orderId;  // 使用 Long 类型
        private String status;
        private Long employeeId;  // 使用 Long 类型
        private String employeeName;
        private String gender;
        private String contactInfo;
        private String position;

        public LogisticsEmployeeSummary() {
        }

        public LogisticsEmployeeSummary(Long logisticsId, String logisticsNumber, Long orderId, String status,
                                        Long employeeId, String employeeName, String gender, String contactInfo, String position) {
            this.logisticsId = logisticsId;
            this.logisticsNumber = logisticsNumber;
            this.orderId = orderId;
            this.status = status;
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.gender = gender;
            this.contactInfo = contactInfo;
            this.position = position;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getContactInfo() {
            return contactInfo;
        }

        public void setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
