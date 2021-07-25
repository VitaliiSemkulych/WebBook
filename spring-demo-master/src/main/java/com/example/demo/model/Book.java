package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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
@ToString(of = {"name","isbn"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book extends AbstractEntity{


    @Column(name = "book_name",nullable = false)
    private String name;
    @Column(name = "page_number")
    private int pageNumber;
    @Column(unique = true,nullable = false)
    private String isbn;
    @Column(name = "issued_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date issuedDate;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private FileInfo image;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    private FileInfo content;
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
    @ManyToOne
    @JoinColumn(name="publisher_id", nullable=false)
    private Publisher publisher;
    @OneToMany(mappedBy="book",fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(mappedBy="book",fetch = FetchType.EAGER)
    private List<Mark> marks;







}
