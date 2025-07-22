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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final ProfileRepository profileRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, ProfileRepository profileRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.profileRepo = profileRepo;
    }


    public List<UserRequestDto> getUsers() {
        List<UserRequestDto> collection = new ArrayList<>();
        List<User> list = userRepo.findAll();
        for (User user : list) {
            collection.add(toDto(user));
        }
        return collection;
    }

    public UserRequestDto getUser(String username) {
        UserRequestDto dto = new UserRequestDto();
        Optional<User> user = userRepo.findById(username);
        if (user.isPresent()){
            dto = toDto(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

//    public boolean userExists(String username) {
//        return userRepo.existsById(username);
//    }

    public User createUser(UserRequestDto userRequestDto, PasswordEncoder encoder) {
        List<ValidationResult> validationResults= new ArrayList<ValidationResult>();

        if( userRepo.findById( userRequestDto.getUsername()).isPresent()){
            validationResults.add( new ValidationResult("username", ValidationResult.NOT_UNIQUE));
        }

        if( profileRepo.findByEmail( userRequestDto.getProfile().getEmail()).isPresent()){
            validationResults.add( new ValidationResult("email", ValidationResult.IN_USE));
        }

        if( validationResults.size() > 0){
            throw new ValidationException( validationResults);
        }

        User user = fromDto(userRequestDto, encoder);

        Set<Role> userRoles = user.getRoles();
        Optional<Role> user_role = roleRepo.findById("ROLE_USER");
        user_role.ifPresent(userRoles::add);


        User newUser = userRepo.save(user);
        Profile profile = addProfile( newUser, userRequestDto.getProfile());

        profileRepo.save(profile);

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


    public Set<Role> getAuthorities(String username) {
        if (!userRepo.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepo.findById(username).get();
        UserRequestDto userRequestDto = toDto(user);
        return userRequestDto.getRoles();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepo.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepo.findById(username).get();
        user.addRole( new Role( authority));
        userRepo.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepo.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepo.findById(username).get();
        Role roleToRemove = user.getRoles().stream().filter((a) -> a.getRolename().equalsIgnoreCase(authority)).findAny().get();
        user.removeRole(roleToRemove);
        userRepo.save(user);
    }

    public static UserRequestDto toDto(User user){

        var dto = new UserRequestDto();

        dto.setUsername( user.getUsername());
        dto.setPassword( user.getPassword());
        dto.setRoles( user.getRoles());
        dto.setProfile( ProfileService.toDto( user.getProfile()));

        return dto;
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
        dto.setProfile( ProfileService.toDto( user.getProfile()));

        return dto;
    }
}
