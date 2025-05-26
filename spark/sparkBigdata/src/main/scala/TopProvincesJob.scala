package com.aiit.xiangmu

import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{StreamingQuery, StreamingQueryListener}

object TopProvincesJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Top5Provinces")
      .master("local[*]")  // 本地模式，生产中可配置为 yarn 或 k8s 等
      .getOrCreate()

    import spark.implicits._

    // JDBC 配置
    val jdbcUrl = "jdbc:mysql://localhost:3306/lyztestzhu"
    val jdbcProps = new java.util.Properties()
    jdbcProps.setProperty("user", "root")
    jdbcProps.setProperty("password", "zuo2933962633")
    jdbcProps.setProperty("driver", "com.mysql.cj.jdbc.Driver")

    // 添加流式查询监听器（可选）
    spark.streams.addListener(new StreamingQueryListener {
      override def onQueryStarted(event: StreamingQueryListener.QueryStartedEvent): Unit = {
        println(s"Query started: ${event.id}")
      }
      override def onQueryProgress(event: StreamingQueryListener.QueryProgressEvent): Unit = {
        println(s"Query progress: ${event.progress}")
      }
      override def onQueryTerminated(event: StreamingQueryListener.QueryTerminatedEvent): Unit = {
        println(s"Query terminated: ${event.id}")
      }
    })

    // 使用 rate source 每秒生成 1 行数据，作为触发器
    val triggerStream = spark.readStream
      .format("rate")
      .option("rowsPerSecond", 1)
      .load()

    // foreachBatch 函数定义
    def processBatch(batchDF: Dataset[Row], batchId: Long): Unit = {
      try {
        // 每次触发都从 MySQL 中读取订单表
        val ordersDF = spark.read
          .jdbc(jdbcUrl, "orders", jdbcProps)

        // 统计收货省份前五名
        val top5 = ordersDF.groupBy($"address")
          .count()
          .orderBy(desc("count"))
          .limit(5)
          .withColumnRenamed("address", "province")
          .withColumnRenamed("count", "total")

        // 将统计结果写入 MySQL 的 top5_provinces 表
        top5.write
          .mode("overwrite") // 覆盖写入；如需累计可改成 append
          .jdbc(jdbcUrl, "top5_provinces", jdbcProps)

        println(s"Batch $batchId processed successfully.")

      } catch {
        case e: Exception =>
          println(s"Error in batch $batchId: ${e.getMessage}")
          e.printStackTrace()
      }
    }

    // 启动流式查询
    val query: StreamingQuery = triggerStream.writeStream
      .outputMode("append")
      .foreachBatch(processBatch _) // 传入处理函数
      .start()

    // 等待直到终止
    try {
      query.awaitTermination()
    } catch {
      case e: Exception =>
        println(s"Streaming query failed: ${e.getMessage}")
        e.printStackTrace()
    }
  }
}
