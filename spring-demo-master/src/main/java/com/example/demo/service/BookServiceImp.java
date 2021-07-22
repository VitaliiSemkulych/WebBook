package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.enums.SearchType;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

import com.example.demo.utils.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    @Value("${number.book.onPage}")
    private int numberBookOnPage;

    private final BookRepository bookRepository;
    private final FileManager fileManager;

    @Override
    public Book getBookByName(String bookName) {
        return bookRepository.findByName(bookName);
    }

    @Override
    public byte[] getContent(String bookName) {
        Book book = bookRepository.findByName(bookName);
        try {
            return fileManager.download(book.getImage().getKey());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }

    }

    @Override
    public Page<BookDTO> selectByPhrase(SearchByPhraseDTO searchPhrase, int page) {
        Page<Book> books = searchPhrase.getSearchType().equals(SearchType.BOOK_NAME)?
                bookRepository.findByNameIsContaining(searchPhrase.getSearchPhrase(),PageRequest.of(page,numberBookOnPage)):
                bookRepository.findByAuthorsNameIsContaining(searchPhrase.getSearchPhrase(),PageRequest.of(page,numberBookOnPage));
        return books.map(book -> {
            try {
                return BookDTO.castBookToBookDTO(book,fileManager.download(book.getImage().getKey()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BookDTO.castBookToBookDTO(book,new byte[0]);
        });


    }

    @Override
    public Page<BookDTO> selectByGenre(String genreName,int page) {

        Page<Book> books = bookRepository.findByGenresName(genreName,PageRequest.of(page,numberBookOnPage));
        return books.map(book -> {
            try {
                return BookDTO.castBookToBookDTO(book,fileManager.download(book.getImage().getKey()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BookDTO.castBookToBookDTO(book,new byte[0]);
        });
    }

    @Override
    @Transactional
    public Page<BookDTO> selectByCharacter(Character character,int page) {
        Page<Book> books = bookRepository.findByNameStartsWith(character,PageRequest.of(page,numberBookOnPage));
        return books.map(book -> {
            try {
                return BookDTO.castBookToBookDTO(book,fileManager.download(book.getImage().getKey()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BookDTO.castBookToBookDTO(book,new byte[0]);
        });

    }



}
