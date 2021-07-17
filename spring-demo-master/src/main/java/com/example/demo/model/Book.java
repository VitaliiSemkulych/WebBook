package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//class which contain model for book
@Entity
@Table(name = "book")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"bookName","isbn"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book extends AbstractEntity{


    @Column(name = "book_name")
    private String bookName;
    @Column(name = "page_number")
    private int pageNumber;
    private byte[] content;
    private String isbn;
    @Column(name = "issued_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date issuedDate;
    private byte[] image;
    private String description;

    //many to many
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Author> authors;
    //many to many
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Genre> genres;

    //many to one
    @ManyToOne
    @JoinColumn(name="publisher_id", nullable=false)
    private Publisher publisher;
    //one to many fetch.type=lazy
    @OneToMany(mappedBy="book",fetch = FetchType.LAZY)
    private List<Comment> comments;
    //one to many fetch.type=eager
    @OneToMany(mappedBy="book",fetch = FetchType.EAGER)
    private List<Mark> marks;







}
