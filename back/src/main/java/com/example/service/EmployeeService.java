package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.Employee;
import com.example.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    public void createEmployee(Employee employee) {
        employeeMapper.insert(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeMapper.selectById(id);
    }

    public List<Employee> selctListemploy() {
        return employeeMapper.selectList(null);
    }

    public void updateEmployee(Employee employee) {
        employeeMapper.updateById(employee);
    }

    public void saveUser(Employee employee) {
        employeeMapper.insert(employee);
    }

    public void delete(Long id) {
        employeeMapper.deleteById(id);
    }

    public Employee selctListemployone(String name) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return employeeMapper.selectOne(wrapper);
    }
}
