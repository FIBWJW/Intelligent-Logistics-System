package com.example.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("logistics") // 指定对应的数据库表名 logistics
public class Logistics {
    @TableId(type = IdType.AUTO) // 主键自增
    private Long id; // 物流记录的唯一标识

    private String logisticsNumber; // 物流编号，唯一标识一条物流记录

    private Long orderId; // 关联订单表的订单编号

    private Long vehicleId; // 关联车辆表的车辆ID

    private Long employeeId; // 关联员工表的配送员ID

    private String recipient; // 收件人

    private String phone;

    private String status; // 物流状态（待配送、配送中、已完成、已取消）

    private String remark;
}
