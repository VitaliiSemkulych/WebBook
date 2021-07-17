package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegisterForm {
    private String userName;
    private String telephoneNumber;
    private String userEmail;
    private String password;
    private MultipartFile image;

}

