package com.example.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("orders") // 对应数据库的订单表
public class Orders {

    @TableId(type = IdType.AUTO) // 主键自增
    private Long id;

    private String orderNumber; // 订单编号

    private String recipientName; // 收件人姓名

    private String recipientPhone; // 收件人电话

    private String address; // 收件地址

    private Timestamp createdAt; // 订单创建时间

    private String status; // 订单状态（待发货、已发货、已完成、已取消）
}
