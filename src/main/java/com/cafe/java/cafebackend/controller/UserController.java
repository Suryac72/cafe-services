package com.cafe.java.cafebackend.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

import jakarta.validation.Valid;


@RequestMapping(path="/user")
@CrossOrigin(origins = "http://localhost:5173")
public interface UserController {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody(required = true) UserProfile userProfile,BindingResult result);

    @PostMapping(path="/login")
    public ResponseEntity<String> login(@Valid @RequestBody(required = true) LoginDTO login,BindingResult result);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();

    @PatchMapping(path="/update")
    public ResponseEntity<String> update(@RequestParam(name = "userId") String userId ,@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path="/checkToken")
    public ResponseEntity<String> checkToken();

    @PatchMapping(path="/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);

    @PostMapping(path="/forgotPassword")
    public  ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);
}