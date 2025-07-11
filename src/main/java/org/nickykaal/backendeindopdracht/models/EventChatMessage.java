package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="event_chat_messages")
public class EventChatMessage {
    @Id
    @GeneratedValue
    @Getter
    Long id_message;

    @JoinColumn(name="id_event")
    @ManyToOne
    Event event;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="id_reply")
    private EventChatMessage message;

    @OneToMany(mappedBy="message")
    @JsonIgnore
    Set<EventChatMessage> replies;
}
