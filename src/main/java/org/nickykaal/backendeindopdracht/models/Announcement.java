package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="announcements")
public class Announcement {
    @Id
    @GeneratedValue
    @Getter
    Long id_announcement;

    @OneToMany(mappedBy="announcement")
    Set<AnnouncementChatMessage> chatMessages;
}
