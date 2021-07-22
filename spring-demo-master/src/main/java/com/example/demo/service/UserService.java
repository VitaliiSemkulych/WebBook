package com.example.demo.service;

import com.example.demo.dto.UpdateUserEmailRequestDTO;
import com.example.demo.dto.UpdateUserImageRequestDTO;
import com.example.demo.dto.UpdateUserInfoRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.Book;
import com.example.demo.model.security.User;
import org.springframework.data.domain.Page;

public interface UserService {
    boolean isUserExist(String isEmailExist);
    UserResponseDTO getUserInfo(String userEmail);
    void updateUserInfo(UpdateUserInfoRequestDTO updateUserInfoRequestDTO);
    boolean updateUserImage(UpdateUserImageRequestDTO updateUserImageRequestDTO);
    boolean updateUserEmail(UpdateUserEmailRequestDTO updateUserEmailRequestDTO);
    User getUserByEmail(String name);

}
