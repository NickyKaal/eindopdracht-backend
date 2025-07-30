package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;
import org.nickykaal.backendeindopdracht.models.Role;

import java.util.Set;

public class UserRequestDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Set<Role> roles;

    @Getter
    @Setter
    private ProfileDto profile;
}