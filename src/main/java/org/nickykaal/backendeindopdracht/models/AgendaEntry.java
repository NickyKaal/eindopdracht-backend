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
    Long id_entry;

    @JoinColumn(name="id_agenda")
    @ManyToOne
    Agenda agenda;

    @JoinColumn(name="id_event")
    @ManyToOne
    Event event;
}
