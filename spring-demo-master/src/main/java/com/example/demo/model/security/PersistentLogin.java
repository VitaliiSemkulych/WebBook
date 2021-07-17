package com.example.demo.model.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "persistent_logins")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersistentLogin {
    @Column(name = "username",nullable = false)
    private String userName;
    @Id
    @Column(nullable = false)
    private String series;
    @Column(nullable = false)
    private String token;
    @Column(name = "last_used",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Data lastUsed;

}
