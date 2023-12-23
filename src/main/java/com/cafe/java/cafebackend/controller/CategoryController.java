package com.cafe.java.cafebackend.controller;

import com.cafe.java.cafebackend.models.Category;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(path="/category")
public interface CategoryController {
    @GetMapping(path = "/get")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(name="filterValue",required = false) String filterValue);

    @GetMapping(path="/get/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") UUID categoryId);

    @PostMapping(path = "/add")
    public ResponseEntity<String> addCategory(@Valid @RequestBody(required = true) Category category, BindingResult result);

    @PatchMapping(path="/update")
    public ResponseEntity<String> updateCategory(@RequestParam(name = "categoryId") UUID categoryId , @RequestBody(required = true)Map<String,String> requestMap);

    @DeleteMapping(path="/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam(name = "categoryId") UUID categoryId);
}
