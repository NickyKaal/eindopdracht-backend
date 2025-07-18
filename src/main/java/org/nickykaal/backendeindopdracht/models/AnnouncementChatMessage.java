package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="announcement_chat_messages")
public class AnnouncementChatMessage {
    @Id
    @GeneratedValue
    @Getter
    Long id_message;

    @JoinColumn(name="id_announcement")
    @ManyToOne
    Announcement announcement;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="id_reply")
    private AnnouncementChatMessage message;

    @OneToMany(mappedBy="message")
    @JsonIgnore
    List<AnnouncementChatMessage> replies;
}
