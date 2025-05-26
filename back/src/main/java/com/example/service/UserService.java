package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pojo.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createUser(User user) {
        userMapper.insert(user);
    }

    public User getUser(Long id) {
        return userMapper.selectById(id);
    }

    public List<User> selectListuser() {
        return userMapper.selectList(null);
    }

    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    public void saveUser(User user) {
        userMapper.insert(user);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    public List<User> selectListuser(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 使用like进行模糊查询
        wrapper.like("username", "%" + name + "%");  // 模糊查询用户名
        List<User> users = userMapper.selectList(wrapper); // 查询所有匹配的记录
        return users;
    }

}
