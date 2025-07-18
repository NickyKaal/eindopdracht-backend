package org.nickykaal.backendeindopdracht.services;


import org.nickykaal.backendeindopdracht.dtos.UserDto;
import org.nickykaal.backendeindopdracht.models.Role;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepos;

    public CustomUserDetailsService(UserService userService, UserRepository repos) {
        this.userRepos = repos;
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> ou = userRepos.findById(username);
        if (ou.isPresent()) {
            User user = ou.get();
            return new CustomUserDetails(user);
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }

}