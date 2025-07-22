package org.nickykaal.backendeindopdracht.services;

import org.nickykaal.backendeindopdracht.dtos.ProfileDto;
import org.nickykaal.backendeindopdracht.models.Profile;

public class ProfileService {

    public static ProfileDto toDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();

        profileDto.setEmail(profile.getEmail());
        profileDto.setGender(profile.getGender());
        profileDto.setFirstname(profile.getFirstname());
        profileDto.setLastname(profile.getLastname());

        return profileDto;
    }

    public static Profile fromDto(ProfileDto profileDto) {
        Profile profile = new Profile();

        profile.setEmail(profileDto.getEmail());
        profile.setGender(profileDto.getGender());
        profile.setFirstname(profileDto.getFirstname());
        profile.setLastname(profileDto.getLastname());

        return profile;
    }
}
