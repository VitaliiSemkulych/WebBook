package com.example.demo.repository;

import com.example.demo.model.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre,Long> {

    Optional<Genre> findByName(String genreName);
}
