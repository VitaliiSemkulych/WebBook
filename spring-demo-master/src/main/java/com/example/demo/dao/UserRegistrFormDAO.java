package com.example.demo.dao;

import com.example.demo.dto.UserRegisterForm;
import com.example.demo.mapper.UserFormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

// data access object, which bring data for registration functionality
@Repository
@Transactional
public class UserRegistrFormDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserRegistrFormDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserRegisterForm findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = "select * from users where userid = ? ";

        Object[] params = new Object[] { userName };
        UserFormMapper mapper = new UserFormMapper();
        try {
            UserRegisterForm userInfo = jdbcTemplate.queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addUserAccount(UserRegisterForm userRegisterForm){
        String sql1 = "INSERT INTO users (userid, password, tephoneNumber, userName,image) "
                + "VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO users_groups (GROUPID,USERID) VALUES (?,?)";

        try {
            jdbcTemplate.update(sql1, userRegisterForm.getUserEmail(), userRegisterForm.getPassword(),
                    userRegisterForm.getTelephoneNumber(), userRegisterForm.getUserName(), userRegisterForm.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        jdbcTemplate.update(sql2,"user", userRegisterForm.getUserEmail());

    }



}
