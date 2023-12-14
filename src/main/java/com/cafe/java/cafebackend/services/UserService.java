package com.cafe.java.cafebackend.services;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;

import jakarta.validation.Valid;



public interface UserService {
    ResponseEntity<String> signUp(@Valid UserProfile userProfile, BindingResult result);

    ResponseEntity<String> login(@Valid LoginDTO login, BindingResult result);
}
