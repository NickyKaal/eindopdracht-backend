package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;

public class AuthDto {

    @Getter
    @Setter
    public String username;

    @Getter
    @Setter
    public String email;

    @Getter
    @Setter
    public String password;
}
