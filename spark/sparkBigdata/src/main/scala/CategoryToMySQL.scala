package com.aiit.xiangmu

//package com.aiit.xiangmu
//
//import org.apache.spark.sql.SparkSession
//import java.io._
//
//object CategoryStatisticsTop5 {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder()
//      .appName("Category Top5 Statistics")
//      .master("local[*]")
//      .getOrCreate()
//
//    val sc = spark.sparkContext
//
//    // 读取数据
//    val data = sc.textFile("Data/xiangmu/shangpin.txt")
//
//    // 获取倒数第三列作为商品种类
//    val categories = data.map { line =>
//      val parts = line.split("\t")
//      if (parts.length >= 3) parts(parts.length - 3).trim else "未知"
//    }
//
//    // 每种类别计数
//    val categoryCounts = categories.map((_, 1)).reduceByKey(_ + _)
//
//    // 转为本地 map 并统计总量
//    val categoryCountMap = categoryCounts.collect().toSeq
//    val totalCount = categoryCountMap.map(_._2).sum.toDouble
//
//    // 降序排序，取前五
//    val top5 = categoryCountMap.sortBy(-_._2).take(5)
//
//    // 剩余合并为“其它”
//    val otherCount = categoryCountMap.filterNot(top5.contains).map(_._2).sum
//    val finalList = top5 :+ ("其它", otherCount)
//
//    // 格式化为百分比字符串
//    val resultLines = finalList.map {
//      case (category, count) =>
//        val percent = (count / totalCount) * 100
//        f"$category, ${percent}%.2f%%"
//    }
//
//    // 写入一个单独的 txt 文件
//    val outputPath = "Data/res/category_top5_statistics.txt"
//    val writer = new PrintWriter(new File(outputPath))
//    resultLines.foreach(writer.println)
//    writer.close()
//
//    println(s"统计结果已写入: $outputPath")
//
//    spark.stop()
//  }
//}
import org.apache.spark.sql.functions.{lit, round, sum}
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.util.Properties

object CategoryToMySQL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Category Percentage to MySQL")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val data = spark.sparkContext.textFile("Data/xiangmu/shangpin.txt")

    val df = data.map(_.split("\t"))
      .filter(_.length >= 7)
      .map(fields => fields(4)) // 倒数第3列，商品种类
      .toDF("category")

    val totalCount = df.count().toDouble

    val categoryPercent = df.groupBy("category").count()
      .withColumn("percentage", round($"count" / totalCount * 100, 2))
      .orderBy($"percentage".desc)
      .select("category", "percentage")

    // 前五
    val top5 = categoryPercent.limit(5)

    // 其他合并为“其他”
    val others = categoryPercent.except(top5)
    val othersSum = others.agg(round(sum("percentage"), 2).as("percentage"))
      .withColumn("category", lit("其他"))
      .select("category", "percentage")

    // 合并最终结果
    val finalResult = top5.union(othersSum)

    // MySQL 配置
    val url = "jdbc:mysql://localhost:3306/lyztestzhu"
    val table = "category_stats"
    val properties = new Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "zuo2933962633")
    properties.setProperty("driver", "com.mysql.cj.jdbc.Driver")

    // 写入数据库（建议覆盖）
    finalResult.repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .jdbc(url, table, properties)

    spark.stop()
  }
}
