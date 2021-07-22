package com.example.demo.exception.enrollExeption;


public class BadActivationCodeException extends Exception{
    private final String activationCode;


    public BadActivationCodeException(String activationCode) {
        super(String.format("Activation code %s is not correct",activationCode));
        this.activationCode = activationCode;
    }
}
