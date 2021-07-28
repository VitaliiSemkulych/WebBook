package com.example.demo.service;

import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.utils.CollectionConvector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PublisherServiceImp implements PublisherService{

    private final PublisherRepository publisherRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Publisher> getPublisher() {
        return CollectionConvector.IterableToList(publisherRepository.findAll());
    }

    @Override
    @Transactional
    public void addPublisher(String publisherName) {
        publisherRepository.save(Publisher.builder()
                .name(publisherName)
                .build());
    }

    @Override
    @Transactional
    public boolean updatePublisher(long publisherId, String newPublisherName) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);
        if(publisherOptional.isPresent()){
            Publisher publisher = publisherOptional.get();
            publisher.setName(newPublisherName);
            publisherRepository.save(publisher);
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePublisher(long publisherId) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);
        if(publisherOptional.isPresent()){
            publisherRepository.deleteById(publisherId);
            return true;
        }else {
            return false;
        }
    }
}
