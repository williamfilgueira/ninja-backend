package com.ninja_br.poc.controller;

import com.ninja_br.poc.model.dto.DashboardStatsResponse;
import com.ninja_br.poc.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public DashboardStatsResponse stats() {
        return dashboardService.getStats();
    }

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of("ok", true);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public Map<String, Object> admin() {
        return Map.of("area", "admin", "ok", true);
    }


}