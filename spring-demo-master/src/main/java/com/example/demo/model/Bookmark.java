package com.example.demo.model;

import com.example.demo.enums.BookmarkType;
import com.example.demo.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_bookmark")
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bookmark extends AbstractEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "bookmark_type")
    private BookmarkType type;

    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private Book book;





}
