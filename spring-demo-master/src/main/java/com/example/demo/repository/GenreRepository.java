package com.example.demo.repository;

import com.example.demo.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Long> {

    Optional<Genre> findByName(String genreName);
}
