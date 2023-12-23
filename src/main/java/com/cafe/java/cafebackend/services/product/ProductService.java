package com.cafe.java.cafebackend.services.product;

import com.cafe.java.cafebackend.dto.ProductDTO;
import com.cafe.java.cafebackend.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    ResponseEntity<List<ProductDTO>> getAllProducts();

    ResponseEntity<String> updateProduct(UUID productId,Map<String, String> requestMap);

    ResponseEntity<String> addProduct(Product product, BindingResult result);

    ResponseEntity<String> deleteProduct(UUID productId);

    ResponseEntity<ProductDTO> getProduct(UUID productId);

    ResponseEntity<List<ProductDTO>> getByCategory(UUID productId);
}
