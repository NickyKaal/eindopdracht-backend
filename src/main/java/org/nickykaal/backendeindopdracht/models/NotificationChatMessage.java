package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
@Table( name="notification_chat_messages")
public class NotificationChatMessage {
    @Id
    @GeneratedValue
    @Getter
    private Long idMessage;

    @JoinColumn(name="idNotification")
    @ManyToOne
    private Notification notification;

    @ManyToOne
    @JoinColumn(name="idReply")
    private NotificationChatMessage message;

    @OneToMany(mappedBy="message")
    private Set<NotificationChatMessage> replies;
}
