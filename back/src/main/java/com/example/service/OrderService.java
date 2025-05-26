package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pojo.Orders;
import com.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    // 添加订单
    public void addOrder(Orders order) {
        orderMapper.insert(order);
    }

    // 获取所有订单
    public List<Orders> getAllOrders() {
        return orderMapper.selectList(null);
    }

    // 根据 ID 获取订单
    public Orders getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    public void updateorders(Orders orders) {
        orderMapper.updateById(orders);
    }

    public void deleteOrderById(Long id) {
        orderMapper.deleteById(id);
    }

    public Orders getOrdersone(String name) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",name);
        return orderMapper.selectOne(wrapper);
    }
}
