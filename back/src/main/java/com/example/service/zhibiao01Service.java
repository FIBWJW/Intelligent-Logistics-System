package com.example.service;

import com.example.mapper.zhibiao01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class zhibiao01Service {
    @Autowired
    zhibiao01 zhibiao01;

    public List<Map<String,Integer>> selectmap(){
        return zhibiao01.selectName();
    }
}
