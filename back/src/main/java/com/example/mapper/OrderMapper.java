package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper  extends BaseMapper<Orders> {
    @Select("SELECT COUNT(*) FROM orders")
    Integer countOrders();

    @Select("SELECT order_number as order_number, created_at as created_at, status FROM orders ORDER BY created_at DESC LIMIT 5")
    List<Orders> selectRecentOrders();
}
