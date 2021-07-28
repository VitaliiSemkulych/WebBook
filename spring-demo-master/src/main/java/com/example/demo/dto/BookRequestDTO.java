package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {
    private BookInfoDTO bookInfoDTO;
    private MultipartFile image;
    private MultipartFile content;

}
