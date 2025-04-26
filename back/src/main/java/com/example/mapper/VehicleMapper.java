package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.Vehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {
    @Select("SELECT COUNT(*) FROM vehicle")
    Integer countVehicles();

    @Select("SELECT license_plate as license_plate,status FROM vehicle ORDER BY status LIMIT 5")
    List<Vehicle> selectVehicleStatus();
}
