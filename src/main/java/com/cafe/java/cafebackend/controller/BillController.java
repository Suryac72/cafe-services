package com.cafe.java.cafebackend.controller;

import com.cafe.java.cafebackend.models.Bill;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/bill")
public interface BillController {

    @PostMapping(path="/generateReport")
    ResponseEntity<String> generateReport(@Valid @RequestBody(required = true) Bill bill, BindingResult result);

    @GetMapping(path="/getBills")
    ResponseEntity<List<Bill>> getBills();

    @PostMapping(path="/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String,Object> requestMap);
}
