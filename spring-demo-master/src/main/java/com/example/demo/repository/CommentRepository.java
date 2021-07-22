package com.example.demo.repository;

import com.example.demo.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    List<Comment> findByBookName(String bookName);
}
