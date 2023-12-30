package com.cafe.java.cafebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cafe.java.cafebackend.services.env.EnvUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication()
@PropertySource(value = "classpath:env.properties", ignoreResourceNotFound = true)
@EnableConfigurationProperties(EnvUtil.class)
public class CafeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeBackendApplication.class, args);
    }
}
