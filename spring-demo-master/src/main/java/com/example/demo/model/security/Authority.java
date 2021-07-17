package com.example.demo.model.security;

import com.example.demo.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"authorityName"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Authority extends AbstractEntity {

    @Column(unique = true, nullable = false, name="authority")
    private String authorityName;

    @ManyToMany(mappedBy = "authorities",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Role> roles;
}
