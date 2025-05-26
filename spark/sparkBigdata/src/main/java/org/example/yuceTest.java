package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;
import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;
import static org.apache.spark.sql.functions.*;

import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
public class yuceTest {
    public static void main(String[] args) throws Exception {
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

        // 从 MySQL 读取数据到 DataFrame
        Dataset<Row> df = spark.read().jdbc(url, table, properties);

        // 显示原始数据
        System.out.println("原始数据：");
        df.show(10, false);

        Dataset<Row> dailyOrders = df.selectExpr("DATE(create_time) as order_date")
                .groupBy("order_date")
                .count()
                .withColumnRenamed("count", "order_count")
                .orderBy("order_date");

        dailyOrders.show(10000,false);

        WindowSpec windowSpec = Window.orderBy("order_date");
        Dataset<Row> withLag = dailyOrders;
        for (int i = 1; i <= 7; i++) {
            withLag = withLag.withColumn("day_" + i, lag(col("order_count"), i).over(windowSpec));
        }

        Dataset<Row> trainingData = withLag
                .filter("day_1 IS NOT NULL AND day_2 IS NOT NULL AND day_3 IS NOT NULL AND day_4 IS NOT NULL AND day_5 IS NOT NULL AND day_6 IS NOT NULL AND day_7 IS NOT NULL")
                .withColumnRenamed("order_count", "label");
        System.out.println("==================");
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"day_7", "day_6", "day_5", "day_4", "day_3", "day_2", "day_1"})
                .setOutputCol("features");
        Dataset<Row> trainingSet = assembler.transform(trainingData).select("features", "label");

        trainingData.show(10);
        LinearRegression lr = new LinearRegression()
                .setFeaturesCol("features")
                .setLabelCol("label")
                .setMaxIter(100)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);
        LinearRegressionModel model = lr.fit(trainingSet);

        System.out.println("Coefficients: " + model.coefficients());
        System.out.println("Intercept: " + model.intercept());

        Dataset<Row> predictions = model.transform(trainingSet);
        predictions.select("features", "label", "prediction").show(10);
        List<Row> last7DaysList = dailyOrders.orderBy(desc("order_date")).limit(7).collectAsList();
        double[] last7Days = new double[7];
        for (int i = 0; i < 7; i++) {
            last7Days[i] = last7DaysList.get(6 - i).getLong(1);  // 倒序取值
        }

        System.out.println("最近7天订单量: " + Arrays.toString(last7Days));
        deleteDFtoDB();
        Connection conn = getConn();
        for (int day = 1; day <= 7; day++) {
            // 构造features向量，注意输入顺序要和训练时一致 day_7 ... day_1
            Vector features = org.apache.spark.ml.linalg.Vectors.dense(
                    last7Days[0], last7Days[1], last7Days[2], last7Days[3], last7Days[4], last7Days[5], last7Days[6]);

            Dataset<Row> predictDF = spark.createDataFrame(
                    Arrays.asList(new PredictionInput(features)),
                    PredictionInput.class);

            Dataset<Row> predictionResult = model.transform(predictDF);
            double predicted = predictionResult.select("prediction").head().getDouble(0);

            System.out.println("预测第" + day + "天订单数：" + predicted);
            String sql = "INSERT INTO ycjg(id,value)" +
                    "VALUES(?,?)";

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, String.valueOf(day));
            pstm.setString(2, String.valueOf(predicted));
            predictionResult.printSchema();
            pstm.addBatch();
            pstm.executeBatch();

            // 更新last7Days数组，移除最旧值，加入预测值，继续预测下一个
            for (int i = 6; i > 0; i--) {
                last7Days[i] = last7Days[i - 1];
            }
            last7Days[0] = predicted;
        }


    }
    public static class PredictionInput implements java.io.Serializable {
        private Vector features;

        public PredictionInput(Vector features) {
            this.features = features;
        }

        public Vector getFeatures() {
            return features;
        }

        public void setFeatures(Vector features) {
            this.features = features;
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

                        String sql = "INSERT INTO ycjg(value)" +
                                "VALUES(?)";

                        PreparedStatement pstm = conn.prepareStatement(sql);
                        int count = 0;

                        while (partition.hasNext()) {
                            Row it = partition.next();


                            pstm.setString(1, it.getString(0)); // task_time

//                            pstm.setLong(2, it.getLong(1)); // position_name


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

            String sql = "DELETE FROM ycjg  WHERE 1 = 1";
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
