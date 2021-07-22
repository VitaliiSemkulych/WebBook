package com.example.demo.model;

import com.example.demo.enums.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
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
}
