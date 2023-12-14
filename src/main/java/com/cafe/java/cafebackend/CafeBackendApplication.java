package com.cafe.java.cafebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cafe.java.cafebackend.services.env.EnvUtil;




@SpringBootApplication()
@EnableConfigurationProperties(EnvUtil.class)
public class CafeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeBackendApplication.class, args);
	}
}
