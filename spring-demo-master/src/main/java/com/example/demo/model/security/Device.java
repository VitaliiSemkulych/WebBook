package com.example.demo.model.security;


import com.example.demo.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "device")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=true,onlyExplicitlyIncluded = true)
@ToString(of = {"deviceInfo"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device extends AbstractEntity {

    @Column(name = "device_information",nullable = false )
    private String deviceInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "device_persistent_logins",
            joinColumns =
                    { @JoinColumn(name = "device_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "persistent_logins_id", referencedColumnName = "series") })
    private PersistentLogin persistentLogin;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;


}
