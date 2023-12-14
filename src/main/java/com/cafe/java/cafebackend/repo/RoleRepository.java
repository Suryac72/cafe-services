package com.cafe.java.cafebackend.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.java.cafebackend.models.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
