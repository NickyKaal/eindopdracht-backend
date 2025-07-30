package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@SequenceGenerator(name="profile_id_seq", initialValue=3)
public class Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="profile_id_seq")
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
    private String email;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String description;

}
