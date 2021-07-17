package com.example.demo.extractor;

import com.example.demo.model.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// extract list of recension from database
@Component
public class RecensionListExtractor implements ResultSetExtractor<List<Comment>> {

    @Override
    public List<Comment> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Comment> recensionList = new ArrayList<>();
        while (resultSet.next()) {
            recensionList.add(new Comment(resultSet.getString("userName"),resultSet.getLong("recension_id"),
                    resultSet.getString("user_id"),
                    resultSet.getLong("book_id"),
                    resultSet.getString("recension_text")));
        }
        return recensionList;
    }
}
