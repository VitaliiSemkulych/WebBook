package com.example.demo.dto;

import com.example.demo.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SelectBookResponseDTO {
    private int selectedPage;
    private int pageNumbers;
    private long selectedBookAmount;
    private List<BookDTO> bookList;


    public static SelectBookResponseDTO getBookResponse(List<BookDTO> bookList, long selectedBookAmount,
                                                              int pageNumbers, int selectedPage){
        return SelectBookResponseDTO.builder()
                .bookList(bookList)
                .selectedBookAmount(selectedBookAmount)
                .pageNumbers(pageNumbers)
                .selectedPage(selectedPage)
                .build();
    }

}
