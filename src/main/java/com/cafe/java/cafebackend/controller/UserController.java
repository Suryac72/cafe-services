package com.cafe.java.cafebackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;



@RequestMapping(path="/user")
public interface UserController {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody(required = true) UserProfile userProfile,BindingResult result);

    @PostMapping(path="/login")
    public ResponseEntity<String> login(@Valid @RequestBody(required = true) LoginDTO login,BindingResult result);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();
}