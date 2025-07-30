package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;

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
}



