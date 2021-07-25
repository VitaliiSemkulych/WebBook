package com.example.demo.handlers;


import com.example.demo.enums.LoginType;
import com.example.demo.model.security.User;
import com.example.demo.repository.security.UserRepository;
import com.example.demo.service.security.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;


@Component
@AllArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        LoginType loginType = LoginType.getLoginTypeByClientName(((OAuth2AuthenticationToken)authentication)
                .getAuthorizedClientRegistrationId());
        User user = getUser((OAuth2User) authentication.getPrincipal(),loginType);
            if (!user.getLoginType().contains(loginType)){
                user.getLoginType().add(loginType);
                user.setUserName(((OAuth2User) authentication.getPrincipal())
                        .<String>getAttribute("name"));
            }
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        httpServletResponse.sendRedirect("/loginSuccess");
    }

    private User getUser(OAuth2User oAuth2User,LoginType loginType){
        return userRepository.findByEmail(oAuth2User.<String>getAttribute("email")).orElseGet(() -> User.builder()
                .userName(oAuth2User.<String>getAttribute("name"))
                .email(oAuth2User.<String>getAttribute("email"))
                .roles(Collections.singletonList(roleService.getUserRole()))
                .loginType(Collections.singletonList(loginType))
                .createAccountDate(new Date())
                .build());
    }

}
