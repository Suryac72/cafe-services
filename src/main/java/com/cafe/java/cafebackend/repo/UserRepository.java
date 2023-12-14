package com.cafe.java.cafebackend.repo;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cafe.java.cafebackend.models.UserProfile;

public interface UserRepository extends JpaRepository<UserProfile,UUID>{
    UserProfile findByUserEmail(String userEmail);
} 
