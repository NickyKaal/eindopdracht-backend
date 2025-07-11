package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table( name="users")
public class User {
    @Id
    @Getter
    @Setter
    String username;

    @Setter
    @Getter
    private String password;


    //https://www.baeldung.com/hibernate-initialize-proxy-exception
    //TODO: use the Hibernate Criteria API ipv , uitzoeken hoe en waar
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "rolename")
    )
    @Getter
    @Setter
    Set<Role> roles = new HashSet<>();

    @OneToMany
    @JsonIgnore
    Set<Friendship> friends;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    Set<Friendship> users;

    @OneToOne(mappedBy="user")
    @JsonIgnore
    Agenda agenda;

    @OneToOne(mappedBy="user")
    @JsonIgnore
    Profile profile;
}
