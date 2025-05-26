package com.example.Controller;

import com.example.pojo.Log;
import com.example.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public void addLog(@RequestBody Log log) {
        logService.addLog(log);
    }

    @GetMapping("/list")
    public Map<String, Object> getLogList(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<Log> logs = logService.getLogs(username, page, pageSize);
        long total = logService.getLogCount(username);

        Map<String, Object> result = new HashMap<>();
        result.put("records", logs);
        result.put("total", total);
        return result;
    }
}