package com.example.demo.service;

import com.example.demo.decoder.DecoderMultipartFileImp;
import com.example.demo.dto.UpdateUserEmailRequestDTO;
import com.example.demo.dto.UpdateUserImageRequestDTO;
import com.example.demo.dto.UpdateUserInfoRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.security.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;


@Service
@AllArgsConstructor
@Log
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final BookRepository bookRepository;

    @Override
    public boolean isUserExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponseDTO getUserInfo(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        return UserResponseDTO.builder()
                .userEmail(user.getEmail())
                .userName(user.getUserName())
                .telephoneNumber(user.getPhoneNumber())
                .image(new DecoderMultipartFileImp(user.getImage()))
                .build();
    }

    @Override
    @Transactional
    public void updateUserInfo(UpdateUserInfoRequestDTO updateUserInfoRequestDTO) {
        User user = userRepository.findByEmail(updateUserInfoRequestDTO.getUserEmail()).get();
        user.setUserName(updateUserInfoRequestDTO.getUserName());
        user.setPhoneNumber(updateUserInfoRequestDTO.getTelephoneNumber());
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = {IOException.class})
    public boolean updateUserImage(UpdateUserImageRequestDTO updateUserImageRequestDTO) {
        User user = userRepository.findByEmail(updateUserImageRequestDTO.getUserEmail()).get();
        try {
            user.setImage(updateUserImageRequestDTO.getImage().getBytes());
            userRepository.save(user);
            return true;
        } catch (IOException e) {
            log.log(Level.WARNING,e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    @Transactional(rollbackFor = {MessagingException.class,UnsupportedEncodingException.class})
    public boolean updateUserEmail(UpdateUserEmailRequestDTO updateUserEmailRequestDTO) {
        User user = userRepository.findByEmail(updateUserEmailRequestDTO.getOldEmail()).get();
        try {
            mailSenderService.sendMime(user);
            user.setEmail(updateUserEmailRequestDTO.getNewEmail());
            userRepository.save(user);
            return true;
        } catch (MessagingException| UnsupportedEncodingException e) {
            log.log(Level.WARNING,e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }



}
