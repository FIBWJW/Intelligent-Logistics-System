//package dataTest;
//
//import org.apache.spark.storage.StorageLevel;
//import org.apache.spark.streaming.receiver.Receiver;
//import scala.collection.mutable.ArrayBuffer;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//public class MySQLReceiver extends Receiver<String> {
//
//    private String jdbcUrl;
//    private String table;
//    private Properties connectionProperties;
//
//    public MySQLReceiver(String jdbcUrl, String table, Properties connectionProperties) {
//        super(StorageLevel.MEMORY_AND_DISK());  // Choose a storage level
//        this.jdbcUrl = jdbcUrl;
//        this.table = table;
//        this.connectionProperties = connectionProperties;
//    }
//
//    // Start fetching data from the database
//    @Override
//    public void onStart() {
//        new Thread(this::fetchData).start();
//    }
//
//    // Stop fetching data
//    @Override
//    public void onStop() {
//        // Handle stopping logic if needed
//    }
//
//    // Fetch data from MySQL and push to Spark streaming context
//    private void fetchData() {
//        try {
//            // Establish MySQL connection
//            Connection connection = DriverManager.getConnection(jdbcUrl, connectionProperties);
//            Statement statement = connection.createStatement();
//
//            // Query to get the latest orders
//            String query = "SELECT address FROM " + table + " WHERE status = '待发货' AND create_time > NOW() - INTERVAL 1 MINUTE";
//            ResultSet resultSet = statement.executeQuery(query);
//
//            // Collect addresses from the query result
//            List<String> addresses = new ArrayList<>();
//            while (resultSet.next()) {
//                addresses.add(resultSet.getString("address"));
//            }
//
//            // Push the addresses to the Spark stream
//            store((ArrayBuffer<String>) addresses);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle exception, maybe retry logic or alerting
//        }
//    }
//}
