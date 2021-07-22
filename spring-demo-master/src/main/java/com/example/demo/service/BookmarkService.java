package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.model.Book;
import com.example.demo.model.security.User;
import org.springframework.data.domain.Page;

public interface BookmarkService {
    boolean isBookBookmark(String bookName, String email, BookmarkType bookmarkType);
    void deleteBookFromBookmark(String bookName, String email, BookmarkType bookmarkType);
    void addBookToBookmark(Book book, User user, BookmarkType bookmarkType);
    Page<BookDTO> selectBookmarkPage(String email, int page, BookmarkType bookmarkType);

}
