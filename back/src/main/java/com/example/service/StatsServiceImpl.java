package com.example.service;

import com.example.mapper.StatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements  StatsService{
    @Autowired
    StatsMapper statsMapper;


    @Override
    public String selectName(int age) {
        return statsMapper.selectName(age);
    }


}
