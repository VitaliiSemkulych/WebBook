package com.example.demo.dto;

import com.example.demo.model.Author;
import com.example.demo.model.Genre;
import com.example.demo.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BookResponseDTO {
    private String name;
    private int pageNumber;
    private String isbn;
    private Date issuedDate;
    private String description;
    private Publisher publisher;
    private List<Genre> genreList;
    private List<Author> authors;
    private MultipartFile image;

}
