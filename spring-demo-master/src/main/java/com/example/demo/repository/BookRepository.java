package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book,Long> {

    Optional<Book> findByName(String bookName);
    Page<Book> findByNameIsContaining(String searchPhrase, PageRequest of);
    Page<Book> findByAuthorsNameIsContaining(String searchPhrase, PageRequest of);
    Page<Book> findByGenresName(String genreName, PageRequest of);
    Page<Book> findByNameStartsWith(Character character, PageRequest of);
}
