package org.nickykaal.backendeindopdracht.repositories;

import org.nickykaal.backendeindopdracht.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository  extends JpaRepository<Notification, String> {
}
