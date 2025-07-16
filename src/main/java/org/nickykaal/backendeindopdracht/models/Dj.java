package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="djs")
public class Dj {
    @Id
    @GeneratedValue
    @Getter
    Long id_dj;

    @ManyToMany
    @JoinTable(
            name = "bookings",
            joinColumns = @JoinColumn(name = "id_dj"),
            inverseJoinColumns = @JoinColumn(name = "id_event")
    )
    Set<Event> bookings;

}
