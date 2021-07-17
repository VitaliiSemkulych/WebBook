package com.example.demo.decoder;

import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//accessory class for decode byte array to MultipartFile type
public class DecoderMultipartFileImp implements MultipartFile {


    private final byte[] imgContent;
    private final String originalFilename;
    private final String name;
    private final String contentType;

    public DecoderMultipartFileImp(byte[] imgContent) {
        this.imgContent = imgContent;
        this.originalFilename = null;
        this.name = null;
        this.contentType = null;
    }

    public DecoderMultipartFileImp(byte[] imgContent, String originalFilename, String name, String contentType) {
        this.imgContent = imgContent;
        this.originalFilename = originalFilename;
        this.name = name;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }


}
