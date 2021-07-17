package com.example.demo.dao;


import com.example.demo.model.Genre;
import com.example.demo.extractor.GenreListResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// data access object, which bring data for functionality related with genre
@Repository
@Transactional
public class GenreDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public GenreDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> getGenreList(){
        String SELECT_GENRE="SELECT  * FROM genre ORDER BY name";
        GenreListResultSetExtractor gle=new GenreListResultSetExtractor();
        return jdbcTemplate.query(SELECT_GENRE,gle);

    }

}
