package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="events")
public class Event {
    @Id
    @GeneratedValue
    @Getter
    Long id_event;

    @OneToMany(mappedBy="event")
    Set<AgendaEntry> scheduledEvents;

    @OneToMany(mappedBy="event")
    Set<EventChatMessage> chatMessages;

    @ManyToMany(mappedBy = "bookings")
    Set<Dj> djs;

    @ManyToMany(mappedBy = "events")
    Set<Genre> genres;

    @JoinColumn(name="id_location")
    @ManyToOne
    Location location;
}
