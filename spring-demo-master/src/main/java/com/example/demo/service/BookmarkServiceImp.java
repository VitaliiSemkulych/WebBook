package com.example.demo.service;

import com.example.demo.dto.BookResponseDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.model.Book;
import com.example.demo.model.Bookmark;
import com.example.demo.model.security.User;
import com.example.demo.repository.BookMarkRepository;
import com.example.demo.utils.FileManager;
import com.example.demo.utils.ModelToDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class BookmarkServiceImp implements BookmarkService{

    @Value("${number.book.onPage}")
    private int numberBookOnPage;

    private final BookMarkRepository bookmarkRepository;
    private final FileManager fileManager;

    @Override
    @Transactional(readOnly = true)
    public boolean isBookBookmark(long bookId, String email, BookmarkType bookmarkType) {
        return bookmarkRepository.existsByUserEmailAndBookIdAndType(email,bookId,bookmarkType);
    }


    @Override
    @Transactional
    public void deleteBookFromBookmark(long bookId, String email, BookmarkType bookmarkType) {
        bookmarkRepository.deleteByUserEmailAndBookIdAndType(email,bookId,bookmarkType);
    }

    @Override
    @Transactional
    public void addBookToBookmark(Book book, User user, BookmarkType bookmarkType) {
        bookmarkRepository.save(Bookmark.builder()
                .book(book)
                .user(user)
                .type(bookmarkType)
                .build());

    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> selectBookmarkPage(String email, int page, BookmarkType bookmarkType) {
        Page<Book> books = bookmarkRepository.findByUserEmailAndType(email,bookmarkType, PageRequest.of(page,numberBookOnPage))
                .map(bookmark -> bookmark.getBook());
        return ModelToDTOConverter.getBookResponseDTOS(books, fileManager);
    }


}
