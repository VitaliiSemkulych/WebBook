package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
    private String userName;
    private String telephoneNumber;
    private String userEmail;
    private MultipartFile image;
}
