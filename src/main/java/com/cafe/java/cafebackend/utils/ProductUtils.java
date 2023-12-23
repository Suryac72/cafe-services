package com.cafe.java.cafebackend.utils;

public class ProductUtils {
    public static String toTitleCase(String text){
        return text.substring(0,1).toUpperCase()+text.substring(1);
    }
}
