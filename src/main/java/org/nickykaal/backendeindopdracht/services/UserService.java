package org.nickykaal.backendeindopdracht.services;

import org.nickykaal.backendeindopdracht.dtos.ProfileDto;
import org.nickykaal.backendeindopdracht.dtos.UserRequestDto;
import org.nickykaal.backendeindopdracht.dtos.UserResponseDto;
import org.nickykaal.backendeindopdracht.exceptions.ValidationException;
import org.nickykaal.backendeindopdracht.models.Profile;
import org.nickykaal.backendeindopdracht.models.Role;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.repositories.ProfileRepository;
import org.nickykaal.backendeindopdracht.repositories.RoleRepository;
import org.nickykaal.backendeindopdracht.repositories.UserRepository;
import org.nickykaal.backendeindopdracht.utils.ValidationResult;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final ProfileRepository profileRepo;
    private final Set<String> genders = Stream.of("male", "female").collect(Collectors.toCollection(HashSet::new));

    public UserService(UserRepository userRepo, RoleRepository roleRepo, ProfileRepository profileRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.profileRepo = profileRepo;
    }

    public List<User> getUsers() {
        List<User> list = userRepo.findByEnabled(true);

        return list;
    }

    public User getUser(String username) {
        Optional<User> user = userRepo.findByUsernameAndEnabled(username, true);
        if (user.isPresent()){
            return user.get();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }

    public User createUser(UserRequestDto userRequestDto, PasswordEncoder encoder) {
        List<ValidationResult> validationResults= new ArrayList<ValidationResult>();

        ProfileDto profileDto = userRequestDto.getProfile();

        if( userRepo.findById( userRequestDto.getUsername()).isPresent()){
            validationResults.add( new ValidationResult("username", ValidationResult.NOT_UNIQUE));
        }

        if( profileRepo.findByEmail( profileDto.getEmail()).isPresent()){
            validationResults.add( new ValidationResult("email", ValidationResult.IN_USE));
        }

        if( profileDto.getGender().describeConstable().isPresent() && !genders.contains(profileDto.getGender())){
            validationResults.add( new ValidationResult("gender", ValidationResult.INVALID));
        }

        if(!validationResults.isEmpty()){
            throw new ValidationException( validationResults);
        }

        User user = fromDto(userRequestDto, encoder);

        Set<Role> userRoles = user.getRoles();
        Optional<Role> user_role = roleRepo.findById("ROLE_USER");
        user_role.ifPresent(userRoles::add);

        User newUser = userRepo.save(user);

        try {
            Profile profile = addProfile( newUser, userRequestDto.getProfile());
            profileRepo.save(profile);
        }
        catch(Exception e){
            userRepo.delete(newUser);
            throw e;
        }

        return newUser;
    }

    public Profile addProfile(User user, Profile profile){

        profile.setUser( user);
        profile = profileRepo.save( profile);

        user.setProfile(profile);

        return profile;
    }

    public Profile addProfile(User user, ProfileDto profileDto){

        return addProfile(user, ProfileService.fromDto( profileDto));
    }


    public void addRole(String username, String authority) {

        if (!userRepo.existsById(username)){
            throw new UsernameNotFoundException(username);
        }

        User user = userRepo.findById(username).get();
        user.addRole( new Role( "ROLE_"+authority));
        userRepo.save(user);
    }

    public void removeRole(String username, String authority) {
        if (!userRepo.existsById(username)){
            throw new UsernameNotFoundException(username);
        }

        User user = userRepo.findById(username).get();
        user.getRoles()
            .stream()
            .filter(
                (a) -> a.getRolename().equalsIgnoreCase("ROLE_"+authority)
            )
            .findAny()
            .ifPresent( role -> {
                user.removeRole(role);
                userRepo.save(user);
            });
    }


    public User fromDto(UserRequestDto userRequestDto, PasswordEncoder encoder) {

        var user = new User();

        user.setUsername(userRequestDto.getUsername());
        user.setPassword(encoder.encode(userRequestDto.getPassword()));

        return user;
    }

    public UserResponseDto toResponseDto(User user) {

        var dto = new UserResponseDto();

        dto.setUsername( user.getUsername());
        dto.setEnabled( user.isEnabled());

        return dto;
    }

    public List<UserResponseDto> toResponseDto(List<User> users) {

        List<UserResponseDto> collection = new ArrayList<>();
        for (User user : users) {
            collection.add(toResponseDto(user));
        }

        return collection;
    }

    public void deleteUser(String username) {
        User user = getUser(username);
        user.setEnabled(false);
        userRepo.save(user);
    }
}
