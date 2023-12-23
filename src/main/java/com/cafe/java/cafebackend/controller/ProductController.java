package com.cafe.java.cafebackend.controller;

import com.cafe.java.cafebackend.dto.ProductDTO;
import com.cafe.java.cafebackend.models.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(path="/product")
public interface ProductController {

    @GetMapping(path = "/get")
    public ResponseEntity<List<ProductDTO>> getAllProducts();

    @GetMapping(path="/getByProductId/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productId") UUID productId);

    @PostMapping(path = "/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody(required = true) Product product, BindingResult result);

    @PatchMapping(path="/update")
    public ResponseEntity<String> updateProduct(@RequestParam(name = "productId") UUID productId , @RequestBody(required = true) Map<String,String> requestMap);

    @DeleteMapping(path="/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "productId") UUID productId);

    @GetMapping(path="/getByCategory/{categoryId}")
    ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable UUID categoryId);
}
