package com.cafe.java.cafebackend.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.java.cafebackend.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

public interface CategoryRepository extends JpaRepository<Category,UUID>{

    @Query(value = "SELECT * FROM category c", nativeQuery = true)
    List<Category> getAllCategories();

    @Query(value = "SELECT * FROM category WHERE category_title = :title", nativeQuery = true)
    Optional<Category> findByCategoryTitle(@Param("title") String title);
} 
