package com.cafe.java.cafebackend.repo;

import com.cafe.java.cafebackend.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
}
