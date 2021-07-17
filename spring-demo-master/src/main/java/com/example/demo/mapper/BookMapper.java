package com.example.demo.mapper;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.model.Publisher;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
// pattern for book mapping
@Component
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setBookName(resultSet.getString("name"));
        book.setAuthor(new Author(resultSet.getLong("author_id"), resultSet.getString("author_name")));
        book.setGenre(new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name")));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPageNumber(resultSet.getInt("page_count"));
        book.setPublishYear(resultSet.getInt("publish_year"));
        book.setPublisher(new Publisher(resultSet.getLong("publisher_id"), resultSet.getString("publisher_name")));
        book.setImage(resultSet.getBytes("image"));
        book.setDescription(resultSet.getString("description"));
        return book;
    }
}
