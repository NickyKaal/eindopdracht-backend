package org.nickykaal.backendeindopdracht.controllers;

import org.nickykaal.backendeindopdracht.dtos.AuthenticationRequestDto;
import org.nickykaal.backendeindopdracht.security.CustomUserDetails;
import org.nickykaal.backendeindopdracht.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;


    private final JwtUtil jwtUtl;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtl = jwtUtl;
    }

    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws Exception {

        UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(up);

            CustomUserDetails ud = (CustomUserDetails) auth.getPrincipal();
            String token = jwtUtl.generateToken(ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

}