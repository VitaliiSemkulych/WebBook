package com.example.demo.model;


import com.example.demo.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "mark")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"mark"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mark extends AbstractEntity{
    @Column(nullable = false)
    private float mark;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
