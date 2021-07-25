package com.example.demo.repository;

import com.example.demo.enums.BookmarkType;
import com.example.demo.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends PagingAndSortingRepository<Bookmark,Long> {
    Page<Bookmark> findByUserEmailAndType(String email, BookmarkType bookmarkType, PageRequest pageRequest);
    boolean existsByUserEmailAndBookIdAndType(String email, long bookId, BookmarkType bookmarkType);
    void deleteByUserEmailAndBookIdAndType(String email, long bookId, BookmarkType bookmarkType);
}
