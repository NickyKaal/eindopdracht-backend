package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @JoinColumn(name="username")
    @Setter
    @Getter
    private User user;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 64)
    private String lastname;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 64)
    private String firstname;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 6)
    private String gender;

    @Getter
    @Setter
    @NotBlank
    @Column(unique = true)
    @Email
    String email;

}
