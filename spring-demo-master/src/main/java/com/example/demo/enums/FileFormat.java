package com.example.demo.enums;

import com.example.demo.exception.UnsupportedSelectTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum FileFormat {
    PDF(List.of(".pdf")), BITMAP(List.of(".bmp")),JPEG (List.of(".jpg", ".jpeg")),TIFF(List.of(".tif", ".tiff")),
    GIF(List.of(".gif")),PNG(List.of(".png")),EPS(List.of(".eps")),
    RAW_IMAGE_FILES(List.of(".raw", ".cr2", ".nef", ".orf", ".sr2"));

    private final List<String> format;

    public static boolean isFormatAvailable(String format){
        return List.of(FileFormat.values()).stream().anyMatch(fileFormat -> fileFormat.getFormat().stream().anyMatch(s -> s.equalsIgnoreCase(format)));
    }

    public static FileFormat fromFormat(String format) {
        for (FileFormat fileFormat : FileFormat.values()) {
            if (fileFormat.getFormat().stream().anyMatch(s -> s.equalsIgnoreCase(format))) {
                return fileFormat;
            }
        }
        throw new UnsupportedSelectTypeException();
    }
}
