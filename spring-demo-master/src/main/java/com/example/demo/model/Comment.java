package com.example.demo.model;

import com.example.demo.model.security.User;
import com.example.demo.utils.TextFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

//class which contain model for book recension
@Entity
@Table(name = "comment")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"commentText"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends AbstractEntity{

    @Column(name = "comment",nullable = false)
    private String commentText;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

}
