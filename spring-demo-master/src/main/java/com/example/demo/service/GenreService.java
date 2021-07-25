package com.example.demo.service;

import com.example.demo.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getGenres();
    void addGenre(String genreName);
    boolean updateGenre(long genreId, String newGenreName);
    boolean deleteGenre(long genreId);

}
