package com.example.demo.dto;

import com.example.demo.constraint.annotation.ExistAuthorsList;
import com.example.demo.constraint.annotation.ExistGenreList;
import com.example.demo.constraint.annotation.ExistPublisher;
import com.example.demo.model.Author;
import com.example.demo.model.Genre;
import com.example.demo.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInfoDTO {
    @NotEmpty(message = "Name is required")
    @Size(min = 1, max = 50)
    private String name;
    @Positive
    private int pageNumber;
    @NotEmpty(message = "ISBN is required")
    private String isbn;
    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Past
    private Date issuedDate;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotNull
    //Exist
    @ExistPublisher
    private Publisher publisher;
    @NotNull
    @ExistAuthorsList
    private List<Author> authors;
    @NotNull
    @ExistGenreList
    private List<Genre> genres;
}
