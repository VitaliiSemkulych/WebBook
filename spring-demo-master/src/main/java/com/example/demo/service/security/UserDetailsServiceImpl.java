package com.example.demo.service.security;

import com.example.demo.model.security.UserDetailImp;
import com.example.demo.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
            return userRepository
                    .findByEmail(userEmail)
                    .map(user-> new UserDetailImp(user))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
}
