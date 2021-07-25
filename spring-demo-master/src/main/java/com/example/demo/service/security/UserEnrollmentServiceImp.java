package com.example.demo.service.security;

import com.example.demo.dto.UserRegisterFormDTO;
import com.example.demo.enums.LoginType;
import com.example.demo.exception.UnsupportedFormatException;
import com.example.demo.exception.enrollExeption.BadActivationCodeException;
import com.example.demo.model.FileInfo;
import com.example.demo.model.security.Role;
import com.example.demo.model.security.User;
import com.example.demo.repository.FileInfoRepository;
import com.example.demo.repository.security.UserRepository;
import com.example.demo.service.MailSenderService;
import com.example.demo.utils.FileManager;
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
    private final FileManager fileManager;
    private final FileInfoRepository fileInfoRepository;


    @Override
    @Transactional(rollbackFor = {IOException.class,MessagingException.class,UnsupportedFormatException.class})
    public void enroll(UserRegisterFormDTO enrollUser)  {
        try {
            FileInfo image = fileInfoRepository.save(FileInfo.fileInfoFactory(enrollUser.getImage()));
            User user = createUser(enrollUser,image,List.of(roleService.getUserRole()),false);
            fileManager.upload(enrollUser.getImage().getBytes(),image.getKey());
            userRepository.save(user);
            mailSenderService.sendMime(user);
        } catch (IOException | MessagingException | UnsupportedFormatException e) {
            log.log(Level.WARNING,e.getMessage());
            e.printStackTrace();
        }

    }

    private User createUser(UserRegisterFormDTO enrollUser, FileInfo image, List<Role> roles , boolean activateAccount){
        return User.builder().userName(enrollUser.getUserName())
                .email(enrollUser.getUserEmail())
                .password(passwordEncoder.encode(enrollUser.getPassword()))
                .phoneNumber(enrollUser.getTelephoneNumber())
                .image(image)
                .roles(roles)
                .active(false)
                .loginType(Stream.of(LoginType.LOCAL).collect(Collectors.toList()))
                .activationCode(UUID.randomUUID().toString())
                .build();
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
