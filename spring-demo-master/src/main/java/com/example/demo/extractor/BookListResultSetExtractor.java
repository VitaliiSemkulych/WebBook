package com.example.demo.extractor;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.model.Publisher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// extract list of book from database
@Component
public class BookListResultSetExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Book> bookList = new ArrayList<>();

        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setBookName(resultSet.getString("name"));
            book.setAuthor(new Author(resultSet.getLong("author_id"), resultSet.getString("author_name")));
            book.setGenre(new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name")));
            book.setIsbn(resultSet.getString("isbn"));
            book.setPageNumber(resultSet.getInt("page_count"));
            book.setPublishYear(resultSet.getInt("publish_year"));
            book.setPublisher(new Publisher(resultSet.getLong("publisher_id"), resultSet.getString("publisher_name")));
            //book.setImage(resultSet.getBytes("image"));
            book.setDescription(resultSet.getString("description"));
            bookList.add(book);
        }
        return bookList;
    }
}
