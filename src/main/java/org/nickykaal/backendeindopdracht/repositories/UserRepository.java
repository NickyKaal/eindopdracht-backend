package org.nickykaal.backendeindopdracht.repositories;

import org.nickykaal.backendeindopdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


}
