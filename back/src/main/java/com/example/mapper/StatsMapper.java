package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface StatsMapper {

    @Select("select name from user")
    public String selectName(int age);
}
