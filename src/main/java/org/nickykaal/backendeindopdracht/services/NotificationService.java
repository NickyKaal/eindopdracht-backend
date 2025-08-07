package org.nickykaal.backendeindopdracht.services;

import org.nickykaal.backendeindopdracht.dtos.NotificationDto;
import org.nickykaal.backendeindopdracht.models.Notification;
import org.nickykaal.backendeindopdracht.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class NotificationService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final NotificationRepository repo;

    public NotificationService(@Value("${my.upload_location.notification}") String fileStorageLocation, NotificationRepository repo) throws IOException {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        Files.createDirectories(fileStoragePath);

        this.repo = repo;
    }

    public Notification fromDto(NotificationDto dto){
        Notification notification = new Notification();

        notification.setContent(dto.getContent());
        notification.setPinned(dto.getPinned());
        notification.setCreated(dto.getCreated());
        notification.setTitle(dto.getTitle());

        return notification;
    }

    public NotificationDto toDto(Notification notification){
        NotificationDto dto = new NotificationDto();

        dto.setContent(notification.getContent());
        dto.setPinned(notification.getPinned());
        dto.setCreated(notification.getCreated());
        dto.setTitle(notification.getTitle());

        return dto;
    }
}
