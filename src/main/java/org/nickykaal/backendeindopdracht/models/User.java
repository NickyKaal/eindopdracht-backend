package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name="users")
public class User {
    @Id
    @Getter
    @Setter
    @NotBlank
    @Size(min=6, max = 64)
    private String username;


//    @Getter
//    @Setter
//    @NotBlank
//    @Column(unique = true)
//    @Email
//    private String email;

    @Setter
    @Getter
    @NotBlank
    private String password;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "rolename")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    @OneToMany
    private Set<Friendship> friends;

    @OneToMany(mappedBy="user")
    private Set<Friendship> users;

    @OneToOne(mappedBy="user")
    private Agenda agenda;

    @Getter
    @Setter
    @OneToOne(mappedBy="user")
    private Profile profile;
}
