package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserInfoRequestDTO {
    @NotEmpty(message = "Name is required")
    private String userName;
    private String telephoneNumber;
    @NotEmpty(message = "Email is required")
    private String userEmail;
}
