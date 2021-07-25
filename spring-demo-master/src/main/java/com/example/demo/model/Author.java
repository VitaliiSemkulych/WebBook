package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "author")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"name"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author extends AbstractEntity{
    @Column(unique = true,nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authors",fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Book> books;
}
