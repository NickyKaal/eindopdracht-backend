package org.nickykaal.backendeindopdracht.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.nickykaal.backendeindopdracht.models.ProfilePicture;

public class ProfileDto {

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String gender;


    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @JsonIgnoreProperties(value = {"contents","contentType"} )
    private ProfilePicture picture;
}



