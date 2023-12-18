package com.cafe.java.cafebackend.models;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user_credentials")
@Table(name="user_credentials")
@Getter()
@Setter()
@AllArgsConstructor()
@NoArgsConstructor()
@Data
@DynamicInsert
@DynamicUpdate
@ToString
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    @Column(name = "user_id")
    private UUID userId;

    @Column(unique = false, name = "user_name")
    @NotBlank(message = "User name cannot be blank")
    private String userName;
    @Column(unique = true, name = "user_email")
    @Email(message = "Invalid email format")
    private String userEmail;
    @Column(name = "user_password")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "User phone number cannot be blank")
    @Column(unique = false, name = "user_phone_no")
    private String userPhoneNo;
    @Column(name = "user_role")
    private String role;
    @Column(name = "user_status")
    private String status;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;

}
