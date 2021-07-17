package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"publisherName"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Publisher extends AbstractEntity{
    @Column(unique = true,nullable = false)
    private String name;

    @OneToMany(mappedBy="publisher",fetch = FetchType.EAGER)
    private List<Book> books;
}
