package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="roles")
public class Role {
    @Id
    @Getter
    @Setter
    private String rolename;


    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Role() {

    }
}