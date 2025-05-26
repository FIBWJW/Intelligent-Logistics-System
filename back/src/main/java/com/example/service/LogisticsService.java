package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pojo.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticsService {
    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    // 添加物流记录
    public void addLogistics(Logistics logistics) {
        logisticsMapper.insert(logistics);
    }

    // 获取所有物流记录
    public List<LogisticsVo> getAllLogistics() {
        ArrayList<LogisticsVo> logisticsVos = new ArrayList<>();
        List<Logistics> logistics = logisticsMapper.selectList(null);
        for (Logistics logistic : logistics) {
            String logisticsNumber = logistic.getLogisticsNumber();
            Orders orders = orderMapper.selectById(logistic.getOrderId());
            String ordersOrderNumber = null;
            String recipient = null;
            String phone = null;
            String address = null;
            if (orders!= null){
                ordersOrderNumber  = orders.getOrderNumber();
                recipient = orders.getRecipientName();
                phone = orders.getRecipientPhone();
                address = orders.getAddress();
            }

            Vehicle vehicle = vehicleMapper.selectById(logistic.getVehicleId());
            String licensePlate = null;
            if (vehicle != null){
                licensePlate = vehicle.getLicensePlate();
            }

            Employee employee = employeeMapper.selectById(logistic.getEmployeeId());
            String employeeName = null;
            if (employee!=null){
                employeeName = employee.getName();
            }


            String status = logistic.getStatus();
            logisticsVos.add(new LogisticsVo(logisticsNumber,ordersOrderNumber,licensePlate,employeeName,recipient,phone,address,status));
        }
        System.out.println(logisticsVos);
        return logisticsVos;
//        return  logisticsMapper.selectList(null);
    }

    // 根据 ID 获取物流记录
    public Logistics getLogisticsById(Long id) {
        return logisticsMapper.selectById(id);
    }

    public void deleteone(String name){
        QueryWrapper<Logistics> wrapper = new QueryWrapper<>();
        wrapper.eq("logistics_number",name);
        logisticsMapper.delete(wrapper);
    }

    public LogisticsVo getAllLogisticsone(String name) {
        QueryWrapper<Logistics> wrapper = new QueryWrapper<>();
        wrapper.eq("logistics_number",name);
        Logistics logistic = logisticsMapper.selectOne(wrapper);
        String logisticsNumber = logistic.getLogisticsNumber();
        Orders orders = orderMapper.selectById(logistic.getOrderId());
        String ordersOrderNumber = null;
        String recipient = null;
        String phone = null;
        String address = null;
        if (orders!= null){
            ordersOrderNumber  = orders.getOrderNumber();
            recipient = orders.getRecipientName();
            phone = orders.getRecipientPhone();
            address = orders.getAddress();
        }

        Vehicle vehicle = vehicleMapper.selectById(logistic.getVehicleId());
        String licensePlate = null;
        if (vehicle != null){
            licensePlate = vehicle.getLicensePlate();
        }

        Employee employee = employeeMapper.selectById(logistic.getEmployeeId());
        String employeeName = null;
        if (employee!=null){
            employeeName = employee.getName();
        }


        String status = logistic.getStatus();
        return new LogisticsVo(logisticsNumber,ordersOrderNumber,licensePlate,employeeName,recipient,phone,address,status);
    }
}
