package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="agenda_entries")
public class AgendaEntry {
    @Id
    @GeneratedValue
    @Getter
    private Long id_entry;

    @JoinColumn(name="id_agenda")
    @ManyToOne
    private Agenda agenda;

    @JoinColumn(name="id_event")
    @ManyToOne
    private Event event;
}
