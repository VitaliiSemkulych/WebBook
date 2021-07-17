package com.example.demo.dao;



import com.example.demo.dto.Login;
import com.example.demo.mapper.LoginUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
// data access object, which bring data for login functionality
@Repository
@Transactional
public class LoginUserDAO  {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public LoginUserDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Login findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = LoginUserMapper.BASE_SQL + " where userid = ? ";

        Object[] params = new Object[] { userName };
        LoginUserMapper mapper = new LoginUserMapper();
        try {
            Login userInfo = jdbcTemplate.queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }




}
