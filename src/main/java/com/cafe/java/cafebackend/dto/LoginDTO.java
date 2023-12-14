package com.cafe.java.cafebackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter()
@Setter()
@AllArgsConstructor()
@NoArgsConstructor()
@Data
@ToString
public class LoginDTO {
    @Email(message = "Invalid email format")
    private String userEmail;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
