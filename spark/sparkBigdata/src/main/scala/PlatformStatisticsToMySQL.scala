package com.aiit.xiangmu

import org.apache.spark.sql.functions.{lit, round, sum}
import org.apache.spark.sql.{SaveMode, SparkSession}
import java.util.Properties

object PlatformStatisticsToMySQL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Platform Statistics to MySQL")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // 读取数据
    val data = spark.sparkContext.textFile("Data/xiangmu/shangpin.txt")

    // 提取平台列（每行最后一列）
    val df = data.map(_.split("\t"))
      .filter(_.nonEmpty)
      .map(fields => fields.last)
      .toDF("platform")

    val totalCount = df.count().toDouble

    val platformPercent = df.groupBy("platform").count()
      .withColumn("percentage", round($"count" / totalCount * 100, 2))
      .orderBy($"percentage".desc)
      .select("platform", "percentage")

    // MySQL 配置
    val url = "jdbc:mysql://localhost:3306/lyztestzhu"
    val table = "platform_stats"
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "zuo2933962633")
    properties.setProperty("driver", "com.mysql.cj.jdbc.Driver")

    // 写入 MySQL
    platformPercent.repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .jdbc(url, table, properties)

    spark.stop()
  }
}
