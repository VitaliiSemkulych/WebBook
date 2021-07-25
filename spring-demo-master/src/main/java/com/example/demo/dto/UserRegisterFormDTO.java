package com.example.demo.dto;

import com.example.demo.constraint.annotation.EmailAlreadyExist;
import com.example.demo.constraint.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegisterFormDTO {
    @NotEmpty(message = "Name is required")
    @Size(min=3, max=30,message = "Name size must be between 3 and 30 characters")
    private String userName;
    @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",
            message="Mobile number is invalid")
    private String telephoneNumber;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email incorrect format")
    @EmailAlreadyExist
    private String userEmail;
    @NotEmpty(message = "Password is required")
    @ValidPassword
    private String password;
    private MultipartFile image;

}

