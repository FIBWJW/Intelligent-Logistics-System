package com.example.Controller;

import com.example.pojo.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api")
    public class RegisterController {

        @Autowired
        private UserMapper userMapper;

        @PostMapping("/register")
        public String register(@RequestBody User user) {
            // 先根据用户名查询
            User existingUser = userMapper.selectByUsername(user.getUsername());
            if (existingUser != null) {
                return "用户名已存在，请换一个";
            }
            // 补充角色和创建时间
            user.setRole("USER"); // 或 "user"，看你数据库里怎么定义
            user.setCreatedat(new Timestamp(System.currentTimeMillis()));

            userMapper.insert(user);
            return "注册成功！";
        }
    }
