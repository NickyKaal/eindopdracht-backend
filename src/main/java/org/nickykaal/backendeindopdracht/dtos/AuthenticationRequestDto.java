package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationRequestDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public AuthenticationRequestDto() {
    }

    public AuthenticationRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}