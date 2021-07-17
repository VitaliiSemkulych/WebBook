package com.example.demo.repository;

import com.example.demo.model.Mark;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark,Long> {
}
