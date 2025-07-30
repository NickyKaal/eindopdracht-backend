package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="locations")
public class Location {
    @Id
    @GeneratedValue
    @Getter
    private Long id_location;

    @OneToMany(mappedBy="location")
    private Set<Event> events;
}
