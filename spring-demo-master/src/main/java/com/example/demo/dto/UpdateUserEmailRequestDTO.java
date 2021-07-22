package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserEmailRequestDTO {
    @NotEmpty(message = "Email is required")
    private String oldEmail;
    @NotEmpty(message = "Email is required")
    private String newEmail;

}
