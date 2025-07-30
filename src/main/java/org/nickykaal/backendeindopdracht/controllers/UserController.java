package org.nickykaal.backendeindopdracht.controllers;

import org.nickykaal.backendeindopdracht.dtos.RolesDto;
import org.nickykaal.backendeindopdracht.dtos.UserRequestDto;
import org.nickykaal.backendeindopdracht.dtos.UserResponseDto;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.security.CustomUserDetailsService;
import org.nickykaal.backendeindopdracht.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(UserService userService, PasswordEncoder encoder, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.encoder = encoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserResponseDto>> getUsers() {

        List<User> users = userService.getUsers();

        return ResponseEntity.ok().body( userService.toResponseDto(users));
    }

    @PostMapping(value = "")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto) {;

        User user = userService.createUser(dto, encoder);

        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.getUsername())
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, location)
                .body(userService.toResponseDto( user));
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("username") String username) {

        try {
            User user = userService.getUser(username);

            return ResponseEntity.ok().body( userService.toResponseDto(user));
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username){
        try {
            userService.deleteUser(username);

            return ResponseEntity.noContent().build();
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{username}/roles")
    public ResponseEntity<Object> getUserRoles(@PathVariable("username") String username) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            return ResponseEntity.ok().body(userDetails.getAuthorities()); //TODO: ROLE_ uit output verwijderen + vertalen met DTO
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{username}/roles")
    public ResponseEntity<Object> addUserRole(@PathVariable("username") String username, @RequestBody RolesDto rolesDto) {
        try {
            for (String rolename : rolesDto.roles) {
                userService.addRole(username, rolename);
            }

            return ResponseEntity.noContent().build();
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{username}/roles/{role}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("role") String role) {
        try {
            userService.removeRole(username, role);
            return ResponseEntity.noContent().build();
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}