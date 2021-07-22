package com.example.demo.repository;

import com.example.demo.enums.BookmarkType;
import com.example.demo.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookMarkRepository extends PagingAndSortingRepository<Bookmark,Long> {
    boolean existsByUserEmailAndBookNameAndType(String email, String bookName, BookmarkType bookmarkType);

    Page<Bookmark> findByUserEmailAndType(String email, BookmarkType bookmarkType, PageRequest pageRequest);

    void deleteByUserEmailAndBookNameAndType(String email, String bookName, BookmarkType bookmarkType);
}
