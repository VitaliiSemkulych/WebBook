package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;

import java.util.List;
// data access object, which bring data for user role functionality
@Repository
@Transactional
public class LoginRoleDAO{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public LoginRoleDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getRoleNames(String userId) {
        String sql = "Select GROUPID " //
                + " from users_groups " //
                + " where USERID = ? ";

        Object[] params = new Object[] { userId };

        List<String> roles = jdbcTemplate.queryForList(sql, params, String.class);

        return roles;
    }


}
