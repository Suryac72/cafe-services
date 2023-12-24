package com.cafe.java.cafebackend.services.dashboard;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DashboardService {
    ResponseEntity<Map<String,Object>> getDashboardDetails();
}
