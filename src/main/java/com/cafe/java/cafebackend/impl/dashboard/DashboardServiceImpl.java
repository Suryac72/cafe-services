package com.cafe.java.cafebackend.impl.dashboard;

import com.cafe.java.cafebackend.repo.BillRepository;
import com.cafe.java.cafebackend.repo.CategoryRepository;
import com.cafe.java.cafebackend.repo.ProductRepository;
import com.cafe.java.cafebackend.services.category.CategoryService;
import com.cafe.java.cafebackend.services.dashboard.DashboardService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BillRepository billRepository;
    @Override
    public ResponseEntity<Map<String, Object>> getDashboardDetails() {
       try{
           Map<String,Object> details = new HashMap<>();
           details.put("categories",categoryRepository.count());
           details.put("products",productRepository.count());
           details.put("bills",billRepository.count());
           return new ResponseEntity<Map<String,Object>>(details,HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
