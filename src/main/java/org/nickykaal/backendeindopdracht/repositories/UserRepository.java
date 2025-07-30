package org.nickykaal.backendeindopdracht.repositories;

import org.nickykaal.backendeindopdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByEnabled(boolean enabled);

    Optional<User> findByUsernameAndEnabled(String username, Boolean enabled);
}
