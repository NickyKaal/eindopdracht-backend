package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="events")
public class Event {
    @Id
    @GeneratedValue
    @Getter
    Long id_event;

    @OneToMany(mappedBy="event")
    List<AgendaEntry> scheduledEvents;

    @OneToMany(mappedBy="event")
    List<EventChatMessage> chatMessages;

    @ManyToMany(mappedBy = "bookings")
    List<Dj> djs;

    @ManyToMany(mappedBy = "events")
    List<Genre> genres;

    @JoinColumn(name="id_location")
    @ManyToOne
    Location location;
}
