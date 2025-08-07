package org.nickykaal.backendeindopdracht.services;

import jakarta.transaction.Transactional;
import org.nickykaal.backendeindopdracht.dtos.ProfileDto;
import org.nickykaal.backendeindopdracht.exceptions.AlreadyExistsException;
import org.nickykaal.backendeindopdracht.exceptions.ResourceNotFoundException;
import org.nickykaal.backendeindopdracht.models.Profile;
import org.nickykaal.backendeindopdracht.models.ProfilePicture;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.repositories.ProfilePictureRepository;
import org.nickykaal.backendeindopdracht.repositories.ProfileRepository;
import org.nickykaal.backendeindopdracht.repositories.RoleRepository;
import org.nickykaal.backendeindopdracht.repositories.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepo;
    private final UserService userService;
    private final ProfilePictureService pictureService;
    private final ProfilePictureService profilePictureService;

    public ProfileService(ProfileRepository profileRepo, UserService userService, ProfilePictureService pictureService, ProfilePictureService profilePictureService) {
        this.profileRepo = profileRepo;
        this.userService = userService;
        this.pictureService = pictureService;
        this.profilePictureService = profilePictureService;
    }

    public Profile getProfile(String username){
        User user = userService.getUser(username);
        return user.getProfile();
    }

    public static ProfileDto toDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();

        profileDto.setEmail(profile.getEmail());
        profileDto.setGender(profile.getGender());
        profileDto.setFirstname(profile.getFirstname());
        profileDto.setLastname(profile.getLastname());
        profileDto.setDescription(profile.getDescription());

        return profileDto;
    }

    public static Profile fromDto(ProfileDto profileDto) {
        Profile profile = new Profile();

        profile.setEmail(profileDto.getEmail());
        profile.setGender(profileDto.getGender());
        profile.setFirstname(profileDto.getFirstname());
        profile.setLastname(profileDto.getLastname());
        profile.setDescription(profileDto.getDescription());

        return profile;
    }

    public boolean isOwner(Profile profile, Authentication authentication){
        return profile.getUser().getUsername().equals( authentication.getName());
    }

    public Profile updateProfile(String username, ProfileDto profileDto, Authentication authentication) {
        Profile profile = getProfile(username);

        if( isOwner(profile, authentication)) {

            if (!(profileDto.getEmail() == null) && !profileDto.getEmail().isBlank()) {
                profile.setEmail(profileDto.getEmail());
            }

            if (profileDto.getGender() != null && !profileDto.getGender().isBlank()) {
                profile.setGender(profileDto.getGender());
            }

            if (profileDto.getFirstname() != null && !profileDto.getFirstname().isBlank()) {
                profile.setFirstname(profileDto.getFirstname());
            }

            if (profileDto.getLastname() != null && !profileDto.getLastname().isBlank()) {
                profile.setLastname(profileDto.getLastname());
            }

            if (profileDto.getDescription() != null && !profileDto.getDescription().isBlank()) {
                profile.setDescription(profileDto.getDescription());
            }

            profile = profileRepo.save(profile);

            return profile;
        }

        throw new ResourceAccessException("Cannot update profile if you are not the owner");
    }


    public String uploadProfilePicture(String username, MultipartFile file, Authentication authentication) throws IOException {
        Profile profile = getProfile(username);

        if( isOwner(profile, authentication)) {

            String contentType = file.getContentType();
            if ( contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
            }

            String url = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(username)
                    .toUriString();

            if( profile.getProfilePicture() != null){
                throw new AlreadyExistsException("There already exists a profile picture");
            }

            ProfilePicture picture = pictureService.storePicture(file, url);

            picture.setProfile(profile);
            pictureService.repo.save(picture);

            return picture.getUrl();
        }

        throw new ResourceAccessException("Cannot update profile if you are not the owner");
    }

    public String updateProfilePicture(String username, MultipartFile file, Authentication authentication) throws IOException {
        Profile profile = getProfile(username);

        if( isOwner(profile, authentication)) {

            String contentType = file.getContentType();
            if ( contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
            }

            ProfilePicture profilePicture = profile.getProfilePicture();

            profilePictureService.updateProfilePicture(file, profilePicture);

            return profilePicture.getUrl();
        }

        throw new ResourceAccessException("Cannot update profile if you are not the owner");
    }

    public ProfilePicture getProfilePicture(String username) {
        Profile profile = getProfile(username);
        return profile.getProfilePicture();

    }

    public Resource getPictureUrlResource(String userName) throws IOException {
        ProfilePicture picture = getProfilePicture(userName);

        return getPictureUrlResource(picture);
    }

    public Resource getPictureUrlResource(ProfilePicture picture) throws IOException {

        return profilePictureService.getPictureUrlResource(picture.getFileName());
    }

    public void deleteProfilePicture(String username, Authentication authentication) throws IOException {
        Profile profile = getProfile(username);

        if( isOwner(profile, authentication)) {

            ProfilePicture profilePicture = profile.getProfilePicture();

            if( profilePicture == null){
                throw new ResourceNotFoundException("profile picture does not exists");
            }

            profile.setProfilePicture(null);
            profileRepo.save(profile);

            profilePictureService.deleteProfilePicture(profilePicture);


        }
        else {
            throw new ResourceAccessException("Cannot update profile if you are not the owner");
        }
    }
}
