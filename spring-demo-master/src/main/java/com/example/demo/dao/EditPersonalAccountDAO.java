package com.example.demo.dao;

import com.example.demo.dto.UserRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
// data access object, which bring data for functionality related upgrade user data
@Repository
@Transactional
public class EditPersonalAccountDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public EditPersonalAccountDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inputChanges(UserRegisterForm user){
        String sql1 = "update users set password=?,tephoneNumber=?,userName=?,image=? where userid=?";

        try {
            jdbcTemplate.update(sql1,user.getPassword(),
                    user.getTelephoneNumber(),user.getUserName(),user.getImage().getBytes(),user.getUserEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
