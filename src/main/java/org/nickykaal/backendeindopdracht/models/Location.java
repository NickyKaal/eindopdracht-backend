package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Entity
@Table( name="locations")
public class Location {
    @Id
    @GeneratedValue
    @Getter
    Long id_location;

    @OneToMany(mappedBy="location")
    Set<Event> events;
}
