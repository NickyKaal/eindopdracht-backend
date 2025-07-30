package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="announcement_chat_messages")
public class AnnouncementChatMessage {
    @Id
    @GeneratedValue
    @Getter
    private Long id_message;

    @JoinColumn(name="id_announcement")
    @ManyToOne
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(name="id_reply")
    private AnnouncementChatMessage message;

    @OneToMany(mappedBy="message")
    private Set<AnnouncementChatMessage> replies;
}
