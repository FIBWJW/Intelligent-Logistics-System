package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface zhibiao01 {

    @Select("select * from  province_order_summary")
    public List<Map<String,Integer>> selectName();

    @Select("select * from logistics_status_summary ")
    public List<Map<String,Integer>> selectName01();

    @Select("select * from employee_order_count ")
    public List<Map<String,Integer>> selectName02();

    @Select("select * from vehicle_usage_count ")
    public List<Map<String,Integer>> selectName03();
}
