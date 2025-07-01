package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="users")
public class User {
    @Id
    @GeneratedValue
    @Getter
    Long id_user;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_group")
    )
    List<Group> groups;

    @OneToMany
    @JsonIgnore
    List<Friendship> friends;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    List<Friendship> users;

    @OneToOne(mappedBy="user")
    @JsonIgnore
    Agenda agenda;
}
