package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="djs")
public class Dj {
    @Id
    @GeneratedValue
    @Getter
    Long id_dj;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "bookings",
            joinColumns = @JoinColumn(name = "id_dj"),
            inverseJoinColumns = @JoinColumn(name = "id_event")
    )
    List<Event> bookings;

}
