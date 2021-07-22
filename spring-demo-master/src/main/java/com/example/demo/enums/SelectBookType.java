package com.example.demo.enums;

import com.example.demo.exception.UnsupportedSelectTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SelectBookType {
    PHRASE("phrase"), CHARACTER("character"), GENRE("genre"), BOOKMARK("bookmark");

    private final String type;

    public static SelectBookType fromType(String text) {
        for (SelectBookType selectBookType : SelectBookType.values()) {
            if (selectBookType.getType().equalsIgnoreCase(text)) {
                return selectBookType;
            }
        }
        throw new UnsupportedSelectTypeException();
    }
}
