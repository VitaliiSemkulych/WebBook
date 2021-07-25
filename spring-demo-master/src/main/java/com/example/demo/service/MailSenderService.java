package com.example.demo.service;

import com.example.demo.model.security.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailSenderService {
    public void sendMime(User user)
            throws MessagingException, UnsupportedEncodingException;
    }
