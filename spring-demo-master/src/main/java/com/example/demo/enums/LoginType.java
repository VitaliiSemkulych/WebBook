package com.example.demo.enums;

import com.example.demo.exception.UnsupportedLoginTypeNameException;
import com.example.demo.exception.UnsupportedSelectTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType {
    LOCAL("local"), GOOGLE("google"), FACEBOOK("facebook");

    private final String loginType;


    public static LoginType getLoginTypeByClientName(String clientName) {
        for (LoginType loginType : LoginType.values()) {
            if (loginType.getLoginType().equalsIgnoreCase(clientName)) {
                return loginType;
            }
        }
        throw new UnsupportedLoginTypeNameException();
    }
}
