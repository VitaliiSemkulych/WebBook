package com.example.demo.repository;

import com.example.demo.model.Publisher;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher,Long> {
}
