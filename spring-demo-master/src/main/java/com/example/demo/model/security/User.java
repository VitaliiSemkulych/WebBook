package com.example.demo.model.security;

import ch.qos.logback.classic.pattern.DateConverter;
import com.example.demo.enums.LoginType;
import com.example.demo.model.AbstractEntity;
import com.example.demo.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)
@ToString(of = {"userName","email"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AbstractEntity {

//Casual endpoint for GOOGLE oauth2 authorization  /oauth2/authorization/google
//Casual endpoint for FACEBOOK oauth2 authorization  /oauth2/authorization/facebook
// <input type="checkbox" name="remember-me" /> Remember Me

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "phone",nullable = true )
    private String phoneNumber;


    @Column(name = "active",nullable = true )
    private Boolean active;

    @Column(name = "create_account_date",nullable = false )
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date createAccountDate;

    @Column(name = "last_login_date",nullable = true )
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    private String activationCode;


    @OneToMany(mappedBy="user")
    private List<Device> devices;

    @ElementCollection(targetClass = LoginType.class)
    @CollectionTable(name="login_types")
    @Column(name = "loginType", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<LoginType> loginType;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Role> roles;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.LAZY)
    @JoinTable(name = "read_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Fetch(value = FetchMode.JOIN)
    private List<Book> readBook;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.LAZY)
    @JoinTable(name = "reading_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Fetch(value = FetchMode.JOIN)
    private List<Book> readingBook;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.LAZY)
    @JoinTable(name = "interesting_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Fetch(value = FetchMode.JOIN)
    private List<Book> interestingBook;



}
