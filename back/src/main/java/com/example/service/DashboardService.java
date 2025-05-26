package com.example.service;

import com.example.pojo.Orders;
import com.example.pojo.Vehicle;
import com.example.mapper.EmployeeMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.UserMapper;
import com.example.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// DashboardService.java
@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final EmployeeMapper employeeMapper;
    private final VehicleMapper vehicleMapper;

    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", userMapper.countUsers());
        result.put("orderCount", orderMapper.countOrders());
        result.put("employeeCount", employeeMapper.countEmployees());
        result.put("vehicleCount", vehicleMapper.countVehicles());
        return result;
    }

    public List<Orders> getRecentOrders() {
        return orderMapper.selectRecentOrders();
    }

    public List<Vehicle> getVehicleStatus() {
        return vehicleMapper.selectVehicleStatus();
    }
}