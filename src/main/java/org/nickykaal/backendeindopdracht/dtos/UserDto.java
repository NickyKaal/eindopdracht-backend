package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    public String username;

    @Getter
    @Setter
    public String password;

    @Getter
    @Setter
    public String[] roles;
}
