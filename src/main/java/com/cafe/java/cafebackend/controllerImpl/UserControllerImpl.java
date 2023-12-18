package com.cafe.java.cafebackend.controllerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.controller.UserController;
import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.services.user.UserService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
            ResponseEntity<List<UserWrapper>> users = userService.getAllUsers();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(@RequestParam(name = "userId") UUID userId,Map<String, String> requestMap) {
      try {
        return userService.updateUser(userId,requestMap);
      } catch (Exception e) {
        e.printStackTrace();
      }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return userService.checkToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changePassword(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return userService.forgotPassword(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
