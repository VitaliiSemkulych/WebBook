package com.example.demo.service.security;


import com.example.demo.dto.UserRegisterFormDTO;
import com.example.demo.exception.enrollExeption.BadActivationCodeException;

public interface UserEnrollmentService {
    public void enroll(UserRegisterFormDTO enrollUser) ;
    public void activateUser(String code) throws BadActivationCodeException;

}
