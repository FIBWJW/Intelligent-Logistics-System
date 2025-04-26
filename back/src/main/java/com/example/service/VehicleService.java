package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.Vehicle;
import com.example.mapper.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;

    // 插入车辆
    public void addVehicle(Vehicle vehicle) {
        vehicleMapper.insert(vehicle);
    }

    // 查询所有车辆
    public List<Vehicle> getAllVehicles() {
        return vehicleMapper.selectList(null);
    }

    // 查询单辆车辆
    public Vehicle getVehicleById(Long id) {
        return vehicleMapper.selectById(id);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleMapper.updateById(vehicle);
    }

    public void delete(Long id) {
        vehicleMapper.deleteById(id);
    }

    public Vehicle getAllVehiclesone(String name) {
        QueryWrapper<Vehicle> wrapper = new QueryWrapper<>();
        wrapper.eq("license_plate",name);
        return vehicleMapper.selectOne(wrapper);
    }
}
