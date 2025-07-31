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


    @Lob
    @Getter
    @Setter
    private byte[] contents;

    public ProfilePicture(String title, String url, String contentType, byte[] contents) {
        this.title = title;
        this.url = url;
        this.contentType = contentType;
        this.contents = contents;
    }
    public ProfilePicture(){

    }
}
