package com.example.Controller;

import com.example.Result;
import com.example.bean.Orders;
import com.example.bean.Vehicle;
import com.example.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/api/dashboard")
    @RequiredArgsConstructor
    public class DashboardController {
        private final DashboardService dashboardService;

        @GetMapping("/statistics")
        public Result<Map<String, Object>> getStatistics() {
            return Result.success(dashboardService.getDashboardStatistics());
        }

        @GetMapping("/recent-orders")
        public Result<List<Orders>> getRecentOrders() {
            return Result.success(dashboardService.getRecentOrders());
        }

        @GetMapping("/vehicle-status")
        public Result<List<Vehicle>> getVehicleStatus() {
            return Result.success(dashboardService.getVehicleStatus());
        }
    }

