package org.nickykaal.backendeindopdracht.controllers;

import org.nickykaal.backendeindopdracht.dtos.NotificationDto;
import org.nickykaal.backendeindopdracht.dtos.UserResponseDto;
import org.nickykaal.backendeindopdracht.models.Notification;
import org.nickykaal.backendeindopdracht.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<NotificationDto>> getNotifications() {

        List<Notification> notifications = notificationService.getNotifications();

        return ResponseEntity.ok().body( notificationService.toDto(notifications));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable("id") Long id) {
        Notification notification = notificationService.getNotification(id);

        return ResponseEntity.ok().body(notificationService.toDto(notification));
    }
}
