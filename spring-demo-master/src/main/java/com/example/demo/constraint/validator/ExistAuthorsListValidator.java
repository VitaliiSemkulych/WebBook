package com.example.demo.constraint.validator;

import com.example.demo.constraint.annotation.ExistAuthorsList;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@AllArgsConstructor
public class ExistAuthorsListValidator implements ConstraintValidator<ExistAuthorsList, List<Author>> {
    private final AuthorRepository authorRepository;
    @Override
    public boolean isValid(List<Author> authors, ConstraintValidatorContext constraintValidatorContext) {
        return authors.stream().allMatch(author -> authorRepository.findById(author.getId()).isPresent());

    }
}
