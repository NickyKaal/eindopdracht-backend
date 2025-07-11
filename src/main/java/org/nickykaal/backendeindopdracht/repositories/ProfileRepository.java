package org.nickykaal.backendeindopdracht.repositories;

import org.nickykaal.backendeindopdracht.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

}
