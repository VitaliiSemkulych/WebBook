package com.example.demo.service;

import com.example.demo.model.Genre;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.utils.CollectionConvector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class GenreServiceImp implements GenreService{

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @PostConstruct
    @Transactional
    private void init(){
        if(!genreRepository.findByName("ALL GENRES").isPresent()) genreRepository.save(
                Genre.builder()
                        .name("ALL GENRES")
                        .books(CollectionConvector.IterableToList(bookRepository.findAll()))
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getGenres() {
        return CollectionConvector.IterableToList(genreRepository.findAll());
    }

    @Override
    @Transactional
    public void addGenre(String genreName) {
        genreRepository.save(Genre.builder().name(genreName).build());

    }

    @Override
    @Transactional
    public boolean updateGenre(long genreId, String newGenreName) {
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if(optionalGenre.isPresent()){
            Genre genre = optionalGenre.get();
            genre.setName(newGenreName);
            genreRepository.save(genre);
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteGenre(long genreId) {
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if(optionalGenre.isPresent()){
            genreRepository.deleteById(genreId);
            return true;
        }else {
            return false;
        }
    }
}
