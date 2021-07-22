package com.example.demo.service.security;

import com.example.demo.dto.UserRegisterFormDTO;
import com.example.demo.enums.LoginType;
import com.example.demo.exception.enrollExeption.BadActivationCodeException;
import com.example.demo.model.security.User;
import com.example.demo.repository.security.UserRepository;
import com.example.demo.service.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Log
@Service
public class UserEnrollmentServiceImp implements UserEnrollmentService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final RoleService roleService;


    @Override
    @Transactional(rollbackFor = {IOException.class,MessagingException.class})
    public void enroll(UserRegisterFormDTO enrollUser)  {


        try {
            User user = User.builder().userName(enrollUser.getUserName())
                    .email(enrollUser.getUserEmail())
                    .password(passwordEncoder.encode(enrollUser.getPassword()))
                    .phoneNumber(enrollUser.getTelephoneNumber())
                    .image(enrollUser.getImage().getBytes())
                    .roles(List.of(roleService.getUserRole()))
                    .active(false)
                    .loginType(Stream.of(LoginType.LOCAL).collect(Collectors.toList()))
                    .activationCode(UUID.randomUUID().toString())
                    .build();
            userRepository.save(user);
            mailSenderService.sendMime(user);
        } catch (IOException | MessagingException e) {
            log.log(Level.WARNING,e.getMessage());
            e.printStackTrace();
        }


    }

    @Transactional(rollbackFor = {BadActivationCodeException.class})
    public void activateUser(String code) throws BadActivationCodeException {
        var user=userRepository.findByActivationCode(code)
                .orElseThrow(()->new BadActivationCodeException(code));
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);

    }

}
