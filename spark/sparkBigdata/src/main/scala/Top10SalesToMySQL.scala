package com.aiit.xiangmu

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{SaveMode, SparkSession}
import java.util.Properties

object Top10SalesToMySQL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Top 10 Sales to MySQL")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // 读取数据
    val data = spark.sparkContext.textFile("Data/xiangmu/shangpin.txt")

    // 提取商品名称和销量（倒数第二列）
    val df = data.map(_.split("\t"))
      .filter(_.length >= 7)
      .map(fields => (fields(0), fields(fields.length - 2).toInt)) // 商品名称 和 销量
      .toDF("name", "sales")

    // 获取销量前10
    val top10 = df.orderBy(col("sales").desc).limit(10)

    // MySQL 配置
    val url = "jdbc:mysql://localhost:3306/lyztestzhu"
    val table = "top10_sales"
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "zuo2933962633")
    properties.setProperty("driver", "com.mysql.cj.jdbc.Driver")

    // 写入 MySQL
    top10.repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .jdbc(url, table, properties)

    spark.stop()
  }
}
