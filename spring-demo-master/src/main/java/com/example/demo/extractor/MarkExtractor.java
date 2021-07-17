package com.example.demo.extractor;

import com.example.demo.model.Mark;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// extract list of mack from database
@Component
public class MarkExtractor implements ResultSetExtractor<List<Mark>> {

    @Override
    public List<Mark> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Mark> bookList = new ArrayList<>();
        while (resultSet.next()) {
             bookList.add(new Mark(resultSet.getInt("mark")));
        }
        return  bookList;
    }
}
