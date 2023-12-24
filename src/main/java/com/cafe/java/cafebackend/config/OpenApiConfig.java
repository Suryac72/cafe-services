package com.cafe.java.cafebackend.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Cafe Management System Backend Services",
                version = "1.0",
                description = "This Documentation contains all required APIs which is used in our Cafe Management System"
        ),
        tags = {
                @Tag(name = "User Services", description = "User/Admin APIs of Cafe-Services"),
                @Tag(name = "Product Services", description = "Product APIs of Cafe-Services"),
                @Tag(name = "Category Services", description = "Category APIs of Cafe-Services"),
                @Tag(name = "Bill Services", description = "Bill Management APIs of Cafe-Services"),
                @Tag(name = "Dashboard Services", description = "Dashboard API of Cafe-Services")
        }
)
public class OpenApiConfig {
}
