package com.example.demo.repository;

import com.example.demo.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    List<Comment> findByBookId(long bookId);
}
