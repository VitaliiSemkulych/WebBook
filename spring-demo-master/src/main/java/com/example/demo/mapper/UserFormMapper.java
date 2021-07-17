package com.example.demo.mapper;


import com.example.demo.dto.UserRegisterForm;

import com.example.demo.decoder.DecoderMultipartFileImp;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;


import java.sql.ResultSet;
import java.sql.SQLException;
// pattern for registration form field mapping
public class UserFormMapper implements RowMapper<UserRegisterForm> {

    @Override
    public UserRegisterForm mapRow(ResultSet resultSet, int i) throws SQLException {
        String userName=resultSet.getString("userName");
        String telephoneNumber=resultSet.getString("tephoneNumber");
        String userEmail=resultSet.getString("userid");
        String password=resultSet.getString("password");
        MultipartFile image= new DecoderMultipartFileImp(resultSet.getBytes("image"));
        return new UserRegisterForm(userName, telephoneNumber, userEmail, password, image);
    }
}
