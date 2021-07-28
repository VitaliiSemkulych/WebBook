package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.utils.CollectionConvector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImp implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthor() {
        return CollectionConvector.IterableToList(authorRepository.findAll());
    }

    @Override
    @Transactional
    public void addAuthor(String authorName) {
        authorRepository.save(Author.builder()
                .name(authorName)
                .build());
    }

    @Override
    @Transactional
    public boolean updateAuthor(long authorId, String newAuthorName) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if(optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();
            author.setName(newAuthorName);
            authorRepository.save(author);
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteAuthor(long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if(optionalAuthor.isPresent()) {
            authorRepository.deleteById(authorId);
            return true;
        }else {
            return false;
        }
    }
}
