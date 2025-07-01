package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table( name="genres")
public class Genre {
    @Id
    @GeneratedValue
    @Getter
    Long id_genre;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "event_genres",
            joinColumns = @JoinColumn(name = "id_genre"),
            inverseJoinColumns = @JoinColumn(name = "id_event")
    )
    List<Event> events;

}
