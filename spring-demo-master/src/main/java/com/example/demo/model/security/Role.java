package com.example.demo.model.security;

import com.example.demo.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"roleName"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends AbstractEntity {

    @Column(unique = true, nullable = false, name="role")
    @NonNull private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<User> users;

}
