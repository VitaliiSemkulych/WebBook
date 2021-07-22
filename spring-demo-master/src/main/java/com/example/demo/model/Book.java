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
@ToString(of = {"name","isbn"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book extends AbstractEntity{


    @Column(name = "book_name",unique = true,nullable = false)
    private String name;
    @Column(name = "page_number")
    private int pageNumber;
    private String isbn;
    @Column(name = "issued_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date issuedDate;
    private String description;

//----------------------------------------------------------------------------
//Used in case of storing document in database
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name="book_logo", columnDefinition="longblob", nullable=true)
//    private byte[] image;
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name="book_content", columnDefinition="longblob", nullable=true)
//    private byte[] content;
//------------------------------------------------------------------------------
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
