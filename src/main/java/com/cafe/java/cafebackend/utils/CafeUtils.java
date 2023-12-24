package com.cafe.java.cafebackend.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Slf4j
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

    public static String generateFileName(){
        Date date = new Date();
        long timestamp = date.getTime();
        String uniqueFileName = "BILL-" + timestamp;
        return uniqueFileName;
    }

    public static JSONArray getJsonArrayFromString(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray;
    }

    public static Map<String,Object> getMapFromJson(String data) {
        if(!Strings.isNullOrEmpty(data))
            return new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){}.getType());
        return new HashMap<>();
    }

    public static boolean isFileExists(String path){
        log.info("Inside isFileExists : {}",path);
        try{
            File file = new File(path);
            return (file!=null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}

