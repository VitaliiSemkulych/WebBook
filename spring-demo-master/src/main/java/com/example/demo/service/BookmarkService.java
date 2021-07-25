package com.example.demo.service;

import com.example.demo.dto.BookResponseDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.model.Book;
import com.example.demo.model.security.User;
import org.springframework.data.domain.Page;

public interface BookmarkService {
    boolean isBookBookmark(long bookId, String email, BookmarkType bookmarkType);
    void deleteBookFromBookmark(long bookId, String email, BookmarkType bookmarkType);
    void addBookToBookmark(Book book, User user, BookmarkType bookmarkType);
    Page<BookResponseDTO> selectBookmarkPage(String email, int page, BookmarkType bookmarkType);

}
