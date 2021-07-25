package com.example.demo.dto;

import com.example.demo.constraint.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginDTO {
    @NotEmpty(message = "Email is required")
    @Email(message = "Email incorrect format")
    private String userEmail;
    @NotEmpty(message = "Password is required")
    @ValidPassword
    private String password;
}
