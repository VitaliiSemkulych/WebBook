package com.example.demo.service;

import com.example.demo.dto.Login;
import com.example.demo.dao.LoginRoleDAO;
import com.example.demo.dao.LoginUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;
//process authentication user to the system
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private LoginUserDAO appUserDAO;

        @Autowired
        private LoginRoleDAO appRoleDAO;

        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            Login appUser = this.appUserDAO.findUserAccount(userName);

            if (appUser == null) {
                System.out.println("User not found! " + userName);
                throw new UsernameNotFoundException("User " + userName + " was not found in the database");
            }


            System.out.println("Found User: " + appUser);

            // [ROLE_USER, ROLE_ADMIN,..]
            List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserEmail());

            List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
            if (roleNames != null) {
                for (String role : roleNames) {
                    // ROLE_USER, ROLE_ADMIN,..
                    GrantedAuthority authority = new SimpleGrantedAuthority(role);
                    grantList.add(authority);
                    System.out.println(role);
                }
            }

            UserDetails userDetails = (UserDetails) new User(appUser.getUserEmail(), //
                    appUser.getPassword(), grantList);

            return userDetails;
        }
}
