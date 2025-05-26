package dataTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test2 {
    public static void main(String[] args) {
        // 数据库连接信息
        String DB_URL = "jdbc:mysql://localhost:3306/lyzTestzhu";
        String DB_USER = "root";
        String DB_PASSWORD = "1234";


        // 文件路径
        String filePath = "C:\\Users\\FIBWJW\\Desktop\\image\\数据来源\\数据\\全国物流词云.txt";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK")))  {



            // 准备 SQL 插入语句
            String sql = "INSERT INTO kdltj (name, region) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            String line;
            // 跳过第一行（如果有标题行）
//             reader.readLine();

            while ((line = reader.readLine()) != null) {
                // 根据你的数据分隔符进行分割，这里是制表符（\t）分隔，根据实际情况修改
                String[] data = line.split(",");
                System.out.println(data[0]);
                // 设置参数
                preparedStatement.setString(1, data[0]);
                preparedStatement.setDouble(2, Double.parseDouble(data[1]));



                // 执行插入
                preparedStatement.executeUpdate();
            }

            System.out.println("数据插入完成！");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
