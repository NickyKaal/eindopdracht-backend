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
    private Long id_event;

    @OneToMany(mappedBy="event")
    private Set<AgendaEntry> scheduledEvents;

    @OneToMany(mappedBy="event")
    private Set<EventChatMessage> chatMessages;

    @ManyToMany(mappedBy = "bookings")
    private Set<Dj> djs;

    @ManyToMany(mappedBy = "events")
    private Set<Genre> genres;

    @JoinColumn(name="id_location")
    @ManyToOne
    private Location location;
}
