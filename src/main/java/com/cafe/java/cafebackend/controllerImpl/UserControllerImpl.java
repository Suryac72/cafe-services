package com.cafe.java.cafebackend.controllerImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.controller.UserController;
import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.services.UserService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    UserService userService;

    @Override 
    public ResponseEntity<String> signUp(@Valid  @RequestBody(required = true) UserProfile userProfile, BindingResult result) {
        try {
            return userService.signUp(userProfile, result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(@Valid LoginDTO login, BindingResult result) {
        try{
            return userService.login(login,result);
        }catch(Exception ex){
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
