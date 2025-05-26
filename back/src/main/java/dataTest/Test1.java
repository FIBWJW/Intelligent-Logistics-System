package dataTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Test1 {
    public static void main(String[] args) {
        // 数据库连接信息
        String DB_URL = "jdbc:mysql://localhost:3306/lyzTestzhu";
        String DB_USER = "root";
        String DB_PASSWORD = "1234";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 创建 SQL 插入语句
            String sql = "INSERT INTO btnldkdxfqt (name, age, orderId) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            Random random = new Random();

            // 随机生成中文名字的字典（姓氏和名字的组合）
            String[] firstName = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许"};
            String[] secondName = {
                    "伟", "芳", "娜", "秀英", "敏", "静", "强", "磊", "彬", "杰",
                    "云", "丹", "康", "复", "彪", "豪", "松", "剑", "锋", "婷",
                    "明", "丽", "强", "超", "伟", "静", "宇", "轩", "婷", "琳",
                    "辉", "芳", "婷", "娜", "伟", "超", "两位", "佳", "玉", "洁",
                    "明", "亮", "鹏", "杰", "秀", "静", "伟", "丽", "鹏", "杰",
                    "强", "俊", "婷", "琳", "静", "超", "鹏", "杰", "婷", "娜"
            };

            // 生成 500 条数据
            for (int i = 0; i < 500; i++) {
                // 随机生成年龄（18 - 90 岁）
                int age = random.nextInt(73) + 18; // random.nextInt(73) 生成 0-72，加 18 后为 18-90

                // 随机生成中文名字
                String name = firstName[random.nextInt(firstName.length)] + secondName[random.nextInt(secondName.length)];

                // 随机生成订单 ID
                int orderId = random.nextInt(1000000); // 假设订单 ID 范围为 0-999999

                // 设置参数并添加到批处理
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setInt(3, orderId);
                preparedStatement.addBatch();

                // 每 100 条执行一次批处理插入
                if ((i + 1) % 100 == 0) {
                    preparedStatement.executeBatch();
                    System.out.println("Inserted " + (i + 1) + " records.");
                }
            }

            // 执行剩余的批处理插入
            if (preparedStatement != null) {
                int[] counts = preparedStatement.executeBatch();
                System.out.println("Inserted " + counts.length + " records.");
            }

            System.out.println("Data generation and insertion completed.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
