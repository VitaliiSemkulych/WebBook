package com.example.demo.model;

import com.example.demo.enums.FileFormat;
import com.example.demo.exception.UnsupportedFormatException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "file_info")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"name"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfo extends AbstractEntity{

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long size;
    @Column(unique = true,nullable = false)
    private String key;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileFormat format;

    @Column(name = "upload_date",nullable = true )
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    public static FileInfo fileInfoFactory(MultipartFile file) throws UnsupportedFormatException {
        if(FileFormat.isFormatAvailable(file.getContentType())) throw new UnsupportedFormatException();
        return FileInfo.builder()
                .name(file.getName())
                .key(generateKey(file.getName()))
                .size(file.getSize())
                .uploadDate(new Date())
                .format(FileFormat.fromFormat(file.getContentType()))
                .build();
    }
    private static String generateKey(String name) {
        return DigestUtils.md5DigestAsHex((name + LocalDateTime.now().toString()).getBytes());
    }
}
