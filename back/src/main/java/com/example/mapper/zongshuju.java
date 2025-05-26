package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface zongshuju {

    @Select("SELECT pt, SUM( xl ) AS total_xl  FROM dyrmsp GROUP BY pt")
    List<HashMap<String, BigDecimal>> countEmployees();

    @Select("SELECT zl, CONVERT(SUM(1), CHAR) AS total_xl  FROM dyrmsp GROUP BY zl LIMIT 5")
    List<HashMap<String, String>> countEmployees1();

    @Select("SELECT \n" +
            "    pl, \n" +
            "    CAST(COUNT(*) AS CHAR) AS total_xl  -- 使用CAST转换为字符串\n" +
            "FROM \n" +
            "    dyrmsp \n" +
            "GROUP BY \n" +
            "    pl\n" +
            "ORDER BY \n" +
            "    COUNT(*) DESC\n" +
            "LIMIT 5")
    List<HashMap<String, String>> countEmployees2();


    @Select("SELECT CASE WHEN age BETWEEN 18 AND 25 THEN '18-25岁' WHEN age BETWEEN 26 AND 35 THEN '26-35岁' WHEN age BETWEEN 36 AND 45 THEN '36-45岁' WHEN age BETWEEN 46 AND 55 THEN '46-55岁' ELSE '56岁以上' END AS age_group, CAST(COUNT(*) AS CHAR) AS order_count FROM btnldkdxfqt GROUP BY CASE WHEN age BETWEEN 18 AND 25 THEN '18-25岁' WHEN age BETWEEN 26 AND 35 THEN '26-35岁' WHEN age BETWEEN 36 AND 45 THEN '36-45岁' WHEN age BETWEEN 46 AND 55 THEN '46-55岁' ELSE '56岁以上' END")
    List<HashMap<String,String>> countEmployees3();

    @Select("select\n" +
            "up_date,CAST(date_count as CHAR) as sunn\n" +
            "FROM\n" +
            "(SELECT up_date, COUNT(*) AS date_count\n" +
            "FROM wlqytop5\n" +
            "GROUP BY up_date\n" +
            "ORDER BY date_count DESC\n" +
            "LIMIT 5) u1")
    List<HashMap<String, String>> countEmployees4();

    @Select("select\n" +
            "pl,CAST(date_count as CHAR) AS sunn\n" +
            "FROM\n" +
            "(SELECT pl, COUNT(*) AS date_count\n" +
            "FROM dyrmsp\n" +
            "GROUP BY pl\n" +
            "ORDER BY date_count DESC\n" +
            "LIMIT 5) u1")
    List<HashMap<String, String>> countEmployees5();


    @Select("SELECT \n" +
            "    CASE \n" +
            "        WHEN age BETWEEN 10 AND 25 THEN '10-25'\n" +
            "        WHEN age BETWEEN 26 AND 40 THEN '26-40'\n" +
            "        WHEN age BETWEEN 41 AND 55 THEN '41-55'\n" +
            "        WHEN age BETWEEN 56 AND 60 THEN '56-60'\n" +
            "        ELSE '60以上'\n" +
            "    END AS age_group,\n" +
            "    COUNT(*) * 100.0 / (SELECT COUNT(*) FROM btnldkdxfqt WHERE age IS NOT NULL) AS percentage\n" +
            "FROM \n" +
            "    btnldkdxfqt\n" +
            "WHERE \n" +
            "    age IS NOT NULL\n" +
            "GROUP BY \n" +
            "    age_group\n" +
            "ORDER BY \n" +
            "    age_group;")
    List<HashMap<String, BigDecimal >> countEmployees6();


    @Select("SELECT \n" +
            "    DATE_FORMAT(create_time, '%Y-%m') AS month,\n" +
            "    COUNT(1) AS order_count\n" +
            "FROM \n" +
            "    orders\n" +
            "GROUP BY \n" +
            "    DATE_FORMAT(create_time, '%Y-%m')\n" +
            "ORDER BY \n" +
            "    month;")
    List<HashMap<String, BigDecimal >> countEmployees7();

    @Select("SELECT name,region FROM `lyztestzhu`.`kdltj` ORDER BY `region` DESC LIMIT 10")
    List<HashMap<String, BigDecimal >> countEmployees8();

    @Select("SELECT \n" +
            "    province,\n" +
            "    count\n" +
            "FROM \n" +
            "    qgtj\n" +
            "ORDER BY \n" +
            "    count DESC LIMIT 10;")
    List<HashMap<String, BigDecimal>> countEmployees9();

    @Select("SELECT * FROM car_type_num LIMIT 10 ;")
    List<HashMap<String, BigDecimal>> countEmployees10();

    @Select("SELECT * from order_time_stats")
    List<HashMap<String, BigDecimal>> countOrderTime();

    @Select("SELECT * FROM superemp Limit 5")
    List<HashMap<String, BigDecimal>> countSuperEmp();


    @Select("SELECT CAST(id AS CHAR) AS id, CAST(value AS UNSIGNED) AS value FROM ycjg")
    List<HashMap<BigInteger, BigInteger>> countOrderPred();

//    @Select("SELECT\n" +
//            "\tDATE_FORMAT( o.create_time, '%Y-%m' ) AS MONTH,\n" +
//            "\tCOUNT( e.gender ) AS male_count \n" +
//            "FROM\n" +
//            "\tlogistics l\n" +
//            "\tLEFT JOIN orders o ON l.order_id = o.id\n" +
//            "\tLEFT JOIN employee e ON l.employee_id = e.id \n" +
//            "WHERE\n" +
//            "\te.gender = 'MALE' \n" +
//            "GROUP BY\n" +
//            "\tDATE_FORMAT( o.create_time, '%Y-%m' );")
//    List<HashMap<String, String>> countEmployees6();
//
//    @Select("SELECT\n" +
//            "\tDATE_FORMAT( o.create_time, '%Y-%m' ) AS MONTH,\n" +
//            "\tCOUNT( e.gender ) AS male_count \n" +
//            "FROM\n" +
//            "\tlogistics l\n" +
//            "\tLEFT JOIN orders o ON l.order_id = o.id\n" +
//            "\tLEFT JOIN employee e ON l.employee_id = e.id \n" +
//            "WHERE\n" +
//            "\te.gender = 'FEMALE' \n" +
//            "GROUP BY\n" +
//            "\tDATE_FORMAT( o.create_time, '%Y-%m' );")
//    List<HashMap<String, String>> countEmployees7();
}
