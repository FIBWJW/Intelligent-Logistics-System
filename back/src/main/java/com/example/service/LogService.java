package com.example.service;

import com.example.pojo.Log;
import com.example.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogMapper logMapper;

    @Autowired
    public LogService(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    public void addLog(Log log) {
        logMapper.insert(log);
    }

    public List<Log> getLogs(String username, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return logMapper.selectPage(username, offset, pageSize);
    }

    public long getLogCount(String username) {
        return logMapper.count(username);
    }
}