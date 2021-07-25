package com.example.demo.service;

import com.example.demo.dto.BookInfoDTO;
import com.example.demo.dto.BookRequestDTO;
import com.example.demo.dto.BookResponseDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.model.Book;
import com.example.demo.model.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Book getBookById(long bookId);
    byte[] getContent(FileInfo bookContentFileInfo);
    Page<BookResponseDTO> selectByPhrase(SearchByPhraseDTO searchPhrase, int page);
    Page<BookResponseDTO> selectByGenre(String genre, int page);
    Page<BookResponseDTO> selectByCharacter(Character character, int page);
    boolean deleteBook(long bookId);
    boolean addBook(BookRequestDTO bookRequest);
    boolean updateBookInfo(long bookId, BookInfoDTO bookInfoDTO);
    boolean updateBookFile(long bookId, MultipartFile image);




}
