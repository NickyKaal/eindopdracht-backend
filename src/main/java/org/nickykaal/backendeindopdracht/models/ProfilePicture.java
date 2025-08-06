package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "id_profile")
    private Profile profile;

    @Getter
    @Setter
    private String title;


    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String contentType;

    @Getter
    @Setter
    private String fileName;

    public ProfilePicture(String title, String url, String contentType, String fileName) {
        this.title = title;
        this.url = url;
        this.contentType = contentType;
        this.fileName = fileName;
    }
    public ProfilePicture(){

    }
}
