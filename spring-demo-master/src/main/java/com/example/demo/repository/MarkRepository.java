package com.example.demo.repository;

import com.example.demo.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark,Long> {
    boolean existsByUserEmailAndBookName(String userEmail,String bookName);
    Iterable<Mark> findByBookName(String bookName);

}
