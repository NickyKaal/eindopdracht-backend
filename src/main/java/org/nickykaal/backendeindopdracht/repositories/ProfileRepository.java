package org.nickykaal.backendeindopdracht.repositories;

import org.nickykaal.backendeindopdracht.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByEmail(String email);
}
