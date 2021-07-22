package com.example.demo.service;

import com.example.demo.model.Genre;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class GenreServiceImp implements GenreService{
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @PostConstruct
    private void init(){
        if(!genreRepository.findByName("ALL GENRES").isPresent()) genreRepository.save(
                Genre.builder()
                        .name("ALL GENRES")
                        .books(StreamSupport
                                .stream(bookRepository.findAll().spliterator(), false)
                                .collect(Collectors.toList()))
                .build());
    }

    @Override
    public List<Genre> getGenres() {
        return StreamSupport
                .stream(genreRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
