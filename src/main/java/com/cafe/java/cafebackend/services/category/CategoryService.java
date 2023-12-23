package com.cafe.java.cafebackend.services.category;

import com.cafe.java.cafebackend.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CategoryService {
    ResponseEntity<List<Category>> getAllCategories(String filterValue);

    ResponseEntity<String> addCategory(Category category, BindingResult result);

    ResponseEntity<String> deleteCategory(UUID categoryId);

    ResponseEntity<String> updateCategory(UUID categoryId,Map<String,String> requestMap);

    ResponseEntity<Category> getCategory(UUID categoryId);
}
