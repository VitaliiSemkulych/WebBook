package com.example.demo.repository;

import com.example.demo.model.Publisher;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends PagingAndSortingRepository<Publisher,Long> {
}
