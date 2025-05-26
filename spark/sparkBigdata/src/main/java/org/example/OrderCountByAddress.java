package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.*;
import scala.Int;


import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
public class OrderCountByAddress {
    public static void main(String[] args) {


        // 配置 Spark
        SparkConf conf = new SparkConf()
                .setAppName("ProvinceCount")
                .setMaster("local[*]");

        // 创建 SparkSession
        SparkSession spark = SparkSession.builder()
                .config(conf)
                .getOrCreate();

        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/lyzTestzhu";
        String user = "root";
        String password = "1234";
        String table = "orders";
        String driver = "com.mysql.cj.jdbc.Driver";

        // 设置 JDBC 配置属性
        java.util.Properties properties = new java.util.Properties();
        properties.put("user", user);
        properties.put("password", password);
        properties.put("driver", driver);
while (true){
    try {
        deleteDFtoDB();
        System.out.println("kkk");
        // 从 MySQL 读取数据到 DataFrame
        Dataset<Row> df = spark.read().jdbc(url, table, properties);

        // 显示原始数据
        System.out.println("原始数据：");
        df.show(10, false);

        // 提取省名并统计每个省的次数
        Dataset<Row> provinceCount = df
                .withColumn("province", functions.regexp_extract(df.col("address"), "^([^,]+)", 1))
                .groupBy("province")
                .count()
                .orderBy(functions.desc("count"));

        // 显示统计结果
        System.out.println("各省出现次数统计：");
        provinceCount.show(100, false);
        saveDFtoDB(provinceCount,1000,4,null);
        provinceCount.printSchema();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        System.out.println("休息时间");
        // 停止 SparkSession
//        spark.stop();
        try {
            Thread.sleep(10000); // 睡眠8分钟 = 480000毫秒
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}




    }

    public static void saveDFtoDB(Dataset<Row> result, int batchSize, int partitions, String out) {

        result.repartition(partitions)
                .toJavaRDD()
                .foreachPartition(new VoidFunction<Iterator<Row>>() {
                    @Override
                    public void call(Iterator<Row> partition) throws Exception {
                        Connection conn = getConn(); // 实现你自己的数据库连接函数
                        conn.setAutoCommit(false);

                        String sql = "INSERT INTO qgtj(province,count)" +
                                "VALUES(?,?)";

                        PreparedStatement pstm = conn.prepareStatement(sql);
                        int count = 0;

                        while (partition.hasNext()) {
                            Row it = partition.next();


                            pstm.setString(1, it.getString(0)); // task_time

                            pstm.setLong(2, it.getLong(1)); // position_name


                            pstm.addBatch();
                            count++;

                            if (count % batchSize == 0) {
                                pstm.executeBatch();
                                conn.commit();
                            }
                        }

                        pstm.executeBatch();
                        conn.commit();
                        pstm.close();
                        conn.close();
                    }
                });
    }

    // 获取数据库连接
    public static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/lyzTestzhu";
        String user = "root";
        String password = "1234";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
    public static void deleteDFtoDB() {
        Connection conn = null;
        PreparedStatement deleteStmt = null;

        try {
            conn = getConn(); // 获取数据库连接

            String sql = "DELETE FROM qgtj WHERE 1 = 1";
            deleteStmt = conn.prepareStatement(sql);
//            deleteStmt.setString(1, "990001");

            deleteStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (deleteStmt != null) deleteStmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}