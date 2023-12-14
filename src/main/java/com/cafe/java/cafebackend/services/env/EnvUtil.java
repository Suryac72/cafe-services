package com.cafe.java.cafebackend.services.env;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cafe")
public record EnvUtil(String secret) {
    
} 
