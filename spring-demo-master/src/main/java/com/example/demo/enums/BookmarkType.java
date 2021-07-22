package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookmarkType {
    READ("read"), READING("reading"), INTERESTING("interesting");

    private final String type;

    public static BookmarkType fromType(String text) {
        for (BookmarkType bookmarkType : BookmarkType.values()) {
            if (bookmarkType.type.equalsIgnoreCase(text)) {
                return bookmarkType;
            }
        }
        return null;
    }
}
