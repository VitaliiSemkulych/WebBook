package com.example.demo.constraint.validator;

import com.example.demo.constraint.annotation.ExistGenreList;
import com.example.demo.model.Genre;
import com.example.demo.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@AllArgsConstructor
public class ExistGenreListValidator implements ConstraintValidator<ExistGenreList, List<Genre>>  {
    private final GenreRepository genreRepository;
    @Override
    public boolean isValid(List<Genre> genres, ConstraintValidatorContext constraintValidatorContext) {
        return genres.stream().allMatch(genre -> genreRepository.findById(genre.getId()).isPresent());
    }
}
