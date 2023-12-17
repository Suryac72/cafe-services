package com.cafe.java.cafebackend.services;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

import jakarta.validation.Valid;



public interface UserService {
    ResponseEntity<String> signUp(@Valid UserProfile userProfile, BindingResult result);

    ResponseEntity<String> login(@Valid LoginDTO login, BindingResult result);

    ResponseEntity<List<UserWrapper>> getAllUsers();

    ResponseEntity<String> updateUser(UUID userId,Map<String,String> requestMap);
}
