package com.cafe.java.cafebackend.impl;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.repo.UserRepository;
import com.cafe.java.cafebackend.services.UserService;
import com.cafe.java.cafebackend.services.auth.CustomerDetailsUserService;
import com.cafe.java.cafebackend.services.auth.JwtFilter;
import com.cafe.java.cafebackend.services.auth.JwtUtil;
import com.cafe.java.cafebackend.utils.CafeUtils;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerDetailsUserService customerDetailsUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> signUp(@Valid UserProfile userProfile, BindingResult result) {

        try {
            if (userProfile.getUserName() == null && userProfile.getUserEmail() == null
                    && userProfile.getPassword() == null && userProfile.getUserPhoneNo() == null) {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (result.hasErrors()) {
                return CafeUtils.handleValidationErrors(result);
            }
            UserProfile user = this.userRepository.findByUserEmail(userProfile.getUserEmail());
            if (Objects.isNull(user)) {
                userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
                userProfile.setCreatedAt(LocalDate.now().toString());
                userProfile.setRole("USER");
                userProfile.setStatus("false");
                this.userRepository.save(userProfile);
                return CafeUtils.getResponseEntity(CafeConstants.USER_REGISTER_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.EMAIL_ALREADY_EXISTS,
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(@Valid LoginDTO login, BindingResult result) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUserEmail(), login.getPassword()));

            if (auth.isAuthenticated()) {
                if (customerDetailsUserService.getUserProfile().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>(
                            "{\"token\":\""
                                    + jwtUtil.generateToken(customerDetailsUserService.getUserProfile().getUserEmail(),
                                            customerDetailsUserService.getUserProfile().getRole())
                                    + "\"}",
                            HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Wait for admin approval", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }
    }

}
