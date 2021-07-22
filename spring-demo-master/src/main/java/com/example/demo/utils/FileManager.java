package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {

    @Value("${file.storage}")
    private String DIRECTORY_PATH;

    public void upload(byte[] resource, String keyName) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH, keyName);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }

    public byte[] download(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource.getInputStream().readAllBytes();
        } else {
            throw new IOException();
        }
    }
    public void delete(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        Files.delete(path);
    }
}

