package com.cafe.java.cafebackend.repo;

import com.cafe.java.cafebackend.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordRepository extends JpaRepository<Password, UUID> {
    Password findPasswordByUserEmail(String email);
}
