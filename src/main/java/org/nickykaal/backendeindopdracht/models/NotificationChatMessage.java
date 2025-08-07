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
    private Long id_message;

    @JoinColumn(name="id_notification")
    @ManyToOne
    private Notification notification;

    @ManyToOne
    @JoinColumn(name="id_reply")
    private NotificationChatMessage message;

    @OneToMany(mappedBy="message")
    private Set<NotificationChatMessage> replies;
}
