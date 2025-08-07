package org.nickykaal.backendeindopdracht.controllers;

import org.nickykaal.backendeindopdracht.dtos.*;
import org.nickykaal.backendeindopdracht.exceptions.ResourceNotFoundException;
import org.nickykaal.backendeindopdracht.models.Profile;
import org.nickykaal.backendeindopdracht.models.ProfilePicture;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.security.CustomUserDetailsService;
import org.nickykaal.backendeindopdracht.services.ProfileService;
import org.nickykaal.backendeindopdracht.services.UserService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

        User user = userService.getUser(username);

        return ResponseEntity.ok().body( userService.toResponseDto(user));

    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username){

        userService.deleteUser(username);

        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/{username}/profile")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("username") String username) {

        Profile profile = profileService.getProfile(username);
        return ResponseEntity.ok().body(ProfileService.toDto(profile));

    }

    @PutMapping(value = "/{username}/profile")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("username") String username, @RequestBody ProfileDto profileDto, Authentication authentication) {

        Profile profile = profileService.updateProfile(username, profileDto, authentication);

        return ResponseEntity.ok().body(ProfileService.toDto(profile));

    }

    @PostMapping(value = "/{username}/profile/picture", consumes=MediaType.ALL_VALUE)
    public ResponseEntity<?> uploadProfilePicture(@PathVariable("username") String username,
                                                           @RequestBody MultipartFile file,
                                                          Authentication authentication) throws IOException {

        profileService.uploadProfilePicture(username, file, authentication);

        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(username)
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, location)
                .build();

    }

    @PutMapping(value = "/{username}/profile/picture", consumes=MediaType.ALL_VALUE)
    public ResponseEntity<?> updateProfilePicture(@PathVariable("username") String username,
                                                           @RequestBody MultipartFile file,
                                                           Authentication authentication) throws IOException{

        profileService.updateProfilePicture(username, file, authentication);

        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(username)
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION, location)
                .build();

    }

    @GetMapping(value = "/{username}/profile/picture")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable("username") String username) throws IOException{

        ProfilePicture picture = profileService.getProfilePicture(username);

        if( picture == null){
            throw new ResourceNotFoundException("profile picture does not exists");
        }

        Resource pictureUrlResource = profileService.getPictureUrlResource(picture);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(picture.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + profileService.getProfilePicture(username).getTitle())
                .body(pictureUrlResource);

    }

    @DeleteMapping(value = "/{username}/profile/picture")
    public ResponseEntity<?> deleteProfilePicture(@PathVariable("username") String username, Authentication authentication) throws IOException{

        profileService.deleteProfilePicture(username, authentication);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(value = "/{username}/roles")
    public ResponseEntity<Object> getUserRoles(@PathVariable("username") String username) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok().body(userDetails.getAuthorities()); //TODO: ROLE_ uit output verwijderen + vertalen met DTO

    }

    @PostMapping(value = "/{username}/roles")
    public ResponseEntity<Object> addUserRole(@PathVariable("username") String username, @RequestBody RolesDto rolesDto) {

        for (String rolename : rolesDto.roles) {
            userService.addRole(username, rolename);
        }

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping(value = "/{username}/roles/{role}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("role") String role) {

        userService.removeRole(username, role);
        return ResponseEntity.noContent().build();

    }
}