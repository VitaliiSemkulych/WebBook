package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.model.Book;
import com.example.demo.model.Bookmark;
import com.example.demo.model.security.User;
import com.example.demo.repository.BookMarkRepository;
import com.example.demo.utils.FileManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
public class BookmarkServiceImp implements BookmarkService{

    @Value("${number.book.onPage}")
    private int numberBookOnPage;

    private final BookMarkRepository bookmarkRepository;
    private final FileManager fileManager;

    @Override
    public boolean isBookBookmark(String bookName, String email, BookmarkType bookmarkType) {
        return bookmarkRepository.existsByUserEmailAndBookNameAndType(email,bookName,bookmarkType);
    }


    @Override
    public void deleteBookFromBookmark(String bookName, String email, BookmarkType bookmarkType) {
        bookmarkRepository.deleteByUserEmailAndBookNameAndType(email,bookName,bookmarkType);
    }

    @Override
    public void addBookToBookmark(Book book, User user, BookmarkType bookmarkType) {
        bookmarkRepository.save(Bookmark.builder()
                .book(book)
                .user(user)
                .type(bookmarkType)
                .build());

    }

    @Override
    public Page<BookDTO> selectBookmarkPage(String email, int page, BookmarkType bookmarkType) {
        Page<Book> books = bookmarkRepository.findByUserEmailAndType(email,bookmarkType, PageRequest.of(page,numberBookOnPage))
                .map(bookmark -> bookmark.getBook());
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
