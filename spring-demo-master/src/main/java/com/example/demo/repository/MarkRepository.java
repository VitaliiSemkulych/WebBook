package com.example.demo.repository;

import com.example.demo.model.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends CrudRepository<Mark,Long> {
    Iterable<Mark> findByBookId(long bookId);
    boolean existsByUserEmailAndBookId(String userEmail, long bookId);
}
