package org.nickykaal.backendeindopdracht.controllers;

import org.nickykaal.backendeindopdracht.dtos.*;
import org.nickykaal.backendeindopdracht.models.Profile;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.security.CustomUserDetailsService;
import org.nickykaal.backendeindopdracht.services.ProfileService;
import org.nickykaal.backendeindopdracht.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final ProfileService profileService;

    public UserController(UserService userService, ProfileService profileService, PasswordEncoder encoder, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.encoder = encoder;
        this.customUserDetailsService = customUserDetailsService;
        this.profileService = profileService;
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

    @GetMapping(value = "/{username}/profile")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("username") String username) {
        try {
            Profile profile = profileService.getProfile(username);
            return ResponseEntity.ok().body(ProfileService.toDto(profile));
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{username}/profile")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("username") String username, @RequestBody ProfileDto profileDto, Authentication authentication) {
        try {
            Profile profile = profileService.updateProfile(username, profileDto, authentication);

            return ResponseEntity.ok().body(ProfileService.toDto(profile));
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{username}/profile/picture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable("username") String username,
//                                                           @RequestBody ProfilePictureDto dto,
                                                           @RequestBody MultipartFile file,
                                                           Authentication authentication){
        try {
//            Profile profile = profileService.uploadProfilePicture(username, dto.getFile(), authentication);
            Profile profile = profileService.uploadProfilePicture(username, file, authentication);

//            return ResponseEntity.ok().body(ProfileService.toDto(profile));
            return ResponseEntity.ok().build();
        }
        catch( UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (IOException e) {

            throw new RuntimeException(e);
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