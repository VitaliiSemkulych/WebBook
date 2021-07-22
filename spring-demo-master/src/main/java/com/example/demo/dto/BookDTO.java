package com.example.demo.dto;

import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BookDTO {
    private String name;
    private int pageNumber;
    private String isbn;
    private Date issuedDate;
    private String description;
    private Publisher publisher;
    private List<Genre> genreList;
    private byte[] image;

    public static BookDTO castBookToBookDTO(Book book, byte [] image){
        return BookDTO.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageNumber(book.getPageNumber())
                .issuedDate(book.getIssuedDate())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .genreList(book.getGenres())
                .image(image)
                .build();
    }
}
