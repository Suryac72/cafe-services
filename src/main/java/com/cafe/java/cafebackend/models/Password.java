package com.cafe.java.cafebackend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.util.UUID;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "password")
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "password_id")
    private UUID passwordId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_hashed_password")
    private String userHashedPassword;

    @Column(name = "user_encrypted_password")
    private String userEncryptedPassword;

}
