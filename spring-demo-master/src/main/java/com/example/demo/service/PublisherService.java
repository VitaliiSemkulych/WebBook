package com.example.demo.service;

import com.example.demo.model.Publisher;


import java.util.List;

public interface PublisherService {
    List<Publisher> getPublisher();
    void addPublisher(String publisherName);
    boolean updatePublisher(long publisherId, String newPublisherName);
    boolean deletePublisher(long publisherId);
}
