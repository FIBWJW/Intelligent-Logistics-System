package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.User;
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

    public List<User> selctListuser() {
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

    public User selctListuserone(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}
