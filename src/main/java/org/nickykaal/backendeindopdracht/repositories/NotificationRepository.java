package org.nickykaal.backendeindopdracht.repositories;

import org.nickykaal.backendeindopdracht.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository  extends JpaRepository<Notification, String> {
    Optional<Notification> findByIdNotificationEquals(Long id);
}
