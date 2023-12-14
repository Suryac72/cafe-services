package com.cafe.java.cafebackend.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class CafeUtils {
    
    private CafeUtils(){

    }

    public static  ResponseEntity<String> getResponseEntity(String responseMessage,HttpStatus httpStatus){
        return new ResponseEntity<>("{\"message\":\""+responseMessage+"\"}",httpStatus);
    }

     public static ResponseEntity<String> handleValidationErrors(BindingResult result) {
        // Extract and format validation error messages
        List<String> errorMessages = result.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return CafeUtils.getResponseEntity(String.join(", ", errorMessages), HttpStatus.BAD_REQUEST);
    }
}

