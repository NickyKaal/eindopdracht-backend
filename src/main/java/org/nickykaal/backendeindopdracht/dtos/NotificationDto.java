package org.nickykaal.backendeindopdracht.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class NotificationDto {

    @Setter
    @Getter
    private Long id_notification;

    @Getter
    @Setter
    private Date created;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Boolean pinned;
}
