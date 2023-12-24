package com.cafe.java.cafebackend.repo;

import com.cafe.java.cafebackend.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
    @Query(value = "SELECT * FROM bill order by bill_id desc", nativeQuery = true)
    List<Bill> getAllBills();

    @Query(value = "SELECT * FROM bill where created_by = :userName order by bill_id desc", nativeQuery = true)
    List<Bill> getBillByUserName(@Param("userName") String userName);

}
