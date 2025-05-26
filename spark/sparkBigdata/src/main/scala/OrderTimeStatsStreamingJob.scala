import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.StreamingQuery

object OrderTimeStatsStreamingJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("OrderTimeStatsStreaming")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val jdbcUrl = "jdbc:mysql://localhost:3306/lyztestzhu"
    val jdbcProps = new java.util.Properties()
    jdbcProps.setProperty("user", "root")
    jdbcProps.setProperty("password", "1234")
    jdbcProps.setProperty("driver", "com.mysql.cj.jdbc.Driver")

    // 用 rate 流模拟触发器，每 10 秒触发一次，可根据需求调整
    val triggerStream = spark.readStream
      .format("rate")
      .option("rowsPerSecond", 1)
      .load()

    def processBatch(batchDF: Dataset[Row], batchId: Long): Unit = {
      try {
        // 每个触发批次都重新读取订单表
        val ordersDF = spark.read.jdbc(jdbcUrl, "orders", jdbcProps)

        val ordersWithPeriod = ordersDF.withColumn("time_period",
          when(hour($"create_time") >= 6 && hour($"create_time") < 12, "早上")
            .when(hour($"create_time") >= 12 && hour($"create_time") < 18, "下午")
            .otherwise("晚上")
        )

        val timeStats = ordersWithPeriod.groupBy("time_period")
          .agg(count("*").alias("order_count"))

        // 写入结果，使用 coalesce(1) 降低连接数
        timeStats.coalesce(1)
          .write
          .mode("overwrite")
          .jdbc(jdbcUrl, "order_time_stats", jdbcProps)

        println(s"Batch $batchId 订单时间段统计写入完成。")

      } catch {
        case e: Exception =>
          println(s"Batch $batchId 处理异常：${e.getMessage}")
          e.printStackTrace()
      }
    }

    val query: StreamingQuery = triggerStream.writeStream
      .outputMode("append")  // 这里模式不影响 foreachBatch
      .foreachBatch(processBatch _)
      .start()

    query.awaitTermination()
  }
}
