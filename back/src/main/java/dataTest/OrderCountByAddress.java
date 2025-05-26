//package dataTest;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.sql.*;
//import scala.Int;
//
//
//import java.sql.*;
//import java.util.Properties;
//public class OrderCountByAddress {
//    public static void main(String[] args) {
//        // 配置 Spark
//        SparkConf conf = new SparkConf()
//                .setAppName("ProvinceCount")
//                .setMaster("local[*]");
//
//        // 创建 SparkSession
//        SparkSession spark = SparkSession.builder()
//                .config(conf)
//                .getOrCreate();
//
//        // 数据库连接信息
//        String url = "jdbc:mysql://localhost:3306/lyzTestzhu";
//        String user = "root";
//        String password = "1234";
//        String table = "orders";
//        String driver = "com.mysql.cj.jdbc.Driver";
//
//        // 设置 JDBC 配置属性
//        java.util.Properties properties = new java.util.Properties();
//        properties.put("user", user);
//        properties.put("password", password);
//        properties.put("driver", driver);
//
//        // 从 MySQL 读取数据到 DataFrame
//        Dataset<Row> df = spark.read().jdbc(url, table, properties);
//
//        // 显示原始数据
//        System.out.println("原始数据：");
//        df.show(10, false);
//
//        // 提取省名并统计每个省的次数
//        Dataset<Row> provinceCount = df
//                .withColumn("province", functions.regexp_extract(df.col("address"), "^([^,]+)", 1))
//                .groupBy("province")
//                .count()
//                .orderBy(functions.desc("count"));
//
//        // 显示统计结果
//        System.out.println("各省出现次数统计：");
//        provinceCount.show(100, false);
//
//        // 停止 SparkSession
//        spark.stop();
//    }
//}