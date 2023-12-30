package com.cafe.java.cafebackend.wrappers;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWrapper {
    private UUID userId;
    private String userName;
    private String userEmail;
    private String userPhoneNo;
    private String status;
    private String role;
}
