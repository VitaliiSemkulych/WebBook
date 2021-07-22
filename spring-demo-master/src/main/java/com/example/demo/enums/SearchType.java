package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {

    BOOK_NAME("BookName"), AUTHOR("Author");

    private final String type;

}
