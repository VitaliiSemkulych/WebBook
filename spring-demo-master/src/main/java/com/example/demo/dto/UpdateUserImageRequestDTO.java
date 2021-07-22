package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserImageRequestDTO {
    @NotEmpty(message = "Email is required")
    private String userEmail;
    private MultipartFile image;
}
