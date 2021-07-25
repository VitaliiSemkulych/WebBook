package com.example.demo.decoder;

import com.example.demo.model.FileInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

//accessory class for decode byte array to MultipartFile type
@AllArgsConstructor
public class DecoderMultipartFileImp implements MultipartFile {

    private final FileInfo fileItem;
    private final byte[] fileBinaries;


    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return fileItem.getName();
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return fileItem.getName();
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return fileItem.getFormat().name();
    }

    @Override
    public boolean isEmpty() {
        return fileBinaries == null || fileBinaries.length == 0;
    }

    @Override
    public long getSize() {
        return fileBinaries.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileBinaries;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileBinaries);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(fileBinaries);
    }


}
