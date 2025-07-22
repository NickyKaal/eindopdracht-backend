package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;
import org.nickykaal.backendeindopdracht.models.Role;

import java.util.Set;

public class UserResponseDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private ProfileDto profile;
}