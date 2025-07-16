package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;
import org.nickykaal.backendeindopdracht.models.Role;

import java.util.Set;

public class UserDto {

    @Getter
    @Setter
    public String username;

    @Getter
    @Setter
    public String password;

    @Getter
    @Setter
    public String email;

    @Getter
    @Setter
    public Set<Role> roles;

}