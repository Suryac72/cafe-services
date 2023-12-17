package com.cafe.java.cafebackend.repo;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cafe.java.cafebackend.models.UserProfile;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<UserProfile, UUID> {
    UserProfile findByUserEmail(String userEmail);

    @Query(value = "SELECT * FROM user_credentials u WHERE u.user_role='USER'", nativeQuery = true)
    List<UserProfile> getAllUsers();

    @Query(value = "SELECT u.user_email FROM user_credentials u WHERE u.user_role='ADMIN'", nativeQuery = true)
    List<String> getAllAdmins();

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_credentials SET user_status = :status, updated_at = :date WHERE user_id = :userId", nativeQuery = true)
    Integer updateStatus(@Param("status") String status,@Param("date") String date, @Param("userId") UUID userId);
}
