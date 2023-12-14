package com.cafe.java.cafebackend.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.java.cafebackend.models.Category;

public interface CategoryRepository extends JpaRepository<Category,UUID>{
    
} 
