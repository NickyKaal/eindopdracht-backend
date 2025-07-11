package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue
    @Getter
    private Integer id_profile;

    @OneToOne
    private User user;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String address;

}
