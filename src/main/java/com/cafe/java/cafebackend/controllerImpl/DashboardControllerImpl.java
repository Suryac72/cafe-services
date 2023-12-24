package com.cafe.java.cafebackend.controllerImpl;

import com.cafe.java.cafebackend.controller.DashboardController;
import com.cafe.java.cafebackend.services.dashboard.DashboardService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Dashboard Services", description = "Dashboard API of Cafe-Services")
public class DashboardControllerImpl implements DashboardController {

    @Autowired
    DashboardService dashboardService;
    @Override
    public ResponseEntity<Map<String, Object>> getDashboardDetails() {
        try{
            return dashboardService.getDashboardDetails();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
