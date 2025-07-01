package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="groups")
public class Group {
    @Id
    @GeneratedValue
    @Getter
    Long id_group;

    @ManyToMany(mappedBy = "groups")
    List<User> users;
}
