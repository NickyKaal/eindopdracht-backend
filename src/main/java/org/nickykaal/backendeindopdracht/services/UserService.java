package org.nickykaal.backendeindopdracht.services;

import org.nickykaal.backendeindopdracht.dtos.UserDto;
import org.nickykaal.backendeindopdracht.exceptions.RecordNotFoundException;
import org.nickykaal.backendeindopdracht.models.Role;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.repositories.RoleRepository;
import org.nickykaal.backendeindopdracht.repositories.UserRepository;
import org.nickykaal.backendeindopdracht.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepos;

    public UserService(UserRepository userRepository, RoleRepository roleRepos) {
        this.userRepository = userRepository;
        this.roleRepos = roleRepos;
    }


    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto, PasswordEncoder encoder) {

        User user = toUser(userDto, encoder);

        Set<Role> userRoles = user.getRoles();
        Optional<Role> user_role = roleRepos.findById("ROLE_USER");

        user_role.ifPresent(userRoles::add);

        User newUser = userRepository.save(user);
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Role> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getRoles();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addRole( new Role( authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Role roleToRemove = user.getRoles().stream().filter((a) -> a.getRolename().equalsIgnoreCase(authority)).findAny().get();
        user.removeRole(roleToRemove);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.roles = user.getRoles();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        return user;
    }

    public User toUser(UserDto userDto, PasswordEncoder encoder) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.password));
        user.setEmail(userDto.getEmail());

        return user;
    }

}
