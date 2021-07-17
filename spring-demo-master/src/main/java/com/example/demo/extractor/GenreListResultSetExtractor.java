package com.example.demo.extractor;

import com.example.demo.model.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// extract list of genre from database
public class GenreListResultSetExtractor implements ResultSetExtractor<List<Genre>> {


    @Override
    public List<Genre> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Genre> list = new ArrayList<>();
        while (resultSet.next()) {
           list.add(new Genre( resultSet.getLong("id"),resultSet.getString("name")));
        }

        return list;

    }
}
