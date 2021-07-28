package com.example.demo.utils;

import com.example.demo.decoder.DecoderMultipartFileImp;
import com.example.demo.dto.BookRequestDTO;
import com.example.demo.dto.BookResponseDTO;
import com.example.demo.dto.UserRegisterFormDTO;
import com.example.demo.enums.LoginType;
import com.example.demo.model.Book;
import com.example.demo.model.FileInfo;
import com.example.demo.model.security.User;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelToDTOConverter {

    public static Page<BookResponseDTO> getBookResponseDTOS(Page<Book> books, FileManager fileManager) {
        return books.map(book -> {
            try {
                return castBookToBookDTO(book,new DecoderMultipartFileImp(book.getImage(),fileManager.download(book.getImage().getKey())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return castBookToBookDTO(book,new DecoderMultipartFileImp(book.getImage(),new byte[0]));
        });
    }

    public static BookResponseDTO castBookToBookDTO(Book book, MultipartFile image){
        return BookResponseDTO.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageNumber(book.getPageNumber())
                .issuedDate(book.getIssuedDate())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .genreList(book.getGenres())
                .authors(book.getAuthors())
                .image(image)
                .build();
    }

    public static Book castBookRequestDTOToBook(BookRequestDTO book, FileInfo image, FileInfo content){
        return Book.builder()
                .name(book.getBookInfoDTO().getName())
                .isbn(book.getBookInfoDTO().getIsbn())
                .pageNumber(book.getBookInfoDTO().getPageNumber())
                .issuedDate(book.getBookInfoDTO().getIssuedDate())
                .description(book.getBookInfoDTO().getDescription())
                .publisher(book.getBookInfoDTO().getPublisher())
                .genres(book.getBookInfoDTO().getGenres())
                .authors(book.getBookInfoDTO().getAuthors())
                .image(image)
                .content(content)
                .build();
    }






}
