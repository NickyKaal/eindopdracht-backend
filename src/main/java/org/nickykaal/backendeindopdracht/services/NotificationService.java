package org.nickykaal.backendeindopdracht.services;

import org.nickykaal.backendeindopdracht.dtos.NotificationDto;
import org.nickykaal.backendeindopdracht.dtos.UserResponseDto;
import org.nickykaal.backendeindopdracht.exceptions.ResourceNotFoundException;
import org.nickykaal.backendeindopdracht.models.Notification;
import org.nickykaal.backendeindopdracht.models.User;
import org.nickykaal.backendeindopdracht.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        notification.setSubtitle(dto.getSubtitle());

        return notification;
    }

    public NotificationDto toDto(Notification notification){
        NotificationDto dto = new NotificationDto();

        dto.setContent(notification.getContent());
        dto.setPinned(notification.getPinned());
        dto.setCreated(notification.getCreated());
        dto.setTitle(notification.getTitle());
        dto.setSubtitle(notification.getSubtitle());
        dto.setId_notification(notification.getIdNotification());

        return dto;
    }

    public List<NotificationDto> toDto(List<Notification> notifications) {

        List<NotificationDto> collection = new ArrayList<>();
        for (Notification notification : notifications) {
            collection.add(toDto(notification));
        }

        return collection;
    }

    public List<Notification> getNotifications() {

        List<Notification> list = repo.findAll();

        return list;
    }

    public Notification getNotification(Long id) {
        Optional<Notification> o = repo.findByIdNotificationEquals(id);

        if(o.isPresent()){
            return o.get();
        }

        throw new ResourceNotFoundException("Notification does not exist");
    }
}
