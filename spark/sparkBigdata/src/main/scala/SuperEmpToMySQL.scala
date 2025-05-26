package com.aiit.xiangmu

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.count
import java.util.Properties

object SuperEmpToMySQL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("SuperEmp Count to MySQL")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // 读取数据
    val data = spark.sparkContext.textFile("Data/xiangmu/wldr.txt")

    // 提取“省份”列（倒数第3列）
    val df = data.map(_.split("\t"))
      .filter(_.length >= 4)
      .map(fields => fields(3)) // 省份在第4列（下标3）
      .toDF("region")

    // 按省份分组计数
    val result = df.groupBy("region")
      .agg(count("*").as("sum"))
      .orderBy($"sum".desc)

    // MySQL 配置
    val url = "jdbc:mysql://localhost:3306/lyztestzhu"
    val table = "superEmp"
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "zuo2933962633")
    properties.setProperty("driver", "com.mysql.cj.jdbc.Driver")

    // 写入 MySQL 表
    result.repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .jdbc(url, table, properties)

    spark.stop()
  }
}
