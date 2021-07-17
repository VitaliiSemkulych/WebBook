package com.example.demo.mapper;

import com.example.demo.dto.Login;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
// pattern for login form field mapping
public class LoginUserMapper  implements RowMapper<Login> {
    public static final String BASE_SQL = "select * from users ";

    @Override
    public Login mapRow(ResultSet resultSet, int i) throws SQLException {
        String userId = resultSet.getString("userid");
        String password = resultSet.getString("password");

        return new Login(userId,password);
    }
}
