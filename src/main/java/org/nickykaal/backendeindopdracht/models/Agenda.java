package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

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
    @JsonIgnore
    List<AgendaEntry> scheduledEvents;
}
