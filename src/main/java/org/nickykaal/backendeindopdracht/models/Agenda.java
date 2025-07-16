package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="agendas")
public class Agenda {
    @Id
    @GeneratedValue
    @Getter
    Long id_agenda;

    @OneToOne
    @JoinColumn(name = "id_user")
    User user;

    @OneToMany(mappedBy="agenda")
    Set<AgendaEntry> scheduledEvents;
}
