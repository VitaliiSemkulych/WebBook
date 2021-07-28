package com.example.demo.service;

import com.example.demo.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthor();
    void addAuthor(String authorName);
    boolean updateAuthor(long authorId, String newAuthorName);
    boolean deleteAuthor(long authorId);

}
