package com.example.demo.constraint.validator;

import com.example.demo.constraint.annotation.ExistPublisher;
import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class ExistPublisherValidator implements ConstraintValidator<ExistPublisher, Publisher> {

    public final PublisherRepository publisherRepository;

    @Override
    public boolean isValid(Publisher publisher, ConstraintValidatorContext constraintValidatorContext) {
        return publisherRepository.findById(publisher.getId()).isPresent();
    }
}
