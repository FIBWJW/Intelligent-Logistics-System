package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface  EmployeeMapper extends BaseMapper<Employee> {
    @Select("SELECT COUNT(*) FROM employee")
    Integer countEmployees();
}
