package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.model.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    Book getBookByName(String bookName);
    byte[] getContent(String bookName);
    Page<BookDTO> selectByPhrase(SearchByPhraseDTO searchPhrase, int page);
    Page<BookDTO> selectByGenre(String genre,int page);
    Page<BookDTO> selectByCharacter(Character character, int page);



}
