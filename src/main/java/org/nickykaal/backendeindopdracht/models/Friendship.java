package org.nickykaal.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Table( name="friendships")
public class Friendship {
    @Id
    @GeneratedValue
    @Getter
    Long id_friendship;

    @Getter
    private Date created;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user")
    User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_friend")
    User friend;
}
