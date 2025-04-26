package com.example.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("vehicle") // 指定与数据库表的映射关系
public class Vehicle {
    @TableId(type = IdType.AUTO) // 主键自增
    private Long id;

    private String licensePlate; // 车牌号

    private String model; // 车辆型号

    private String status; // 车辆状态（IN_USE, AVAILABLE）


}
