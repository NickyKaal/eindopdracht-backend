package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table( name="notifications")
@SequenceGenerator(name="notification_id_seq", initialValue=10)
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="notification_id_seq")
    @Getter
    private Long idNotification;

    @OneToMany(mappedBy="notification")
    private Set<NotificationChatMessage> chatMessages;

    @Getter
    @Setter
    private Date created;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 90)
    private String title;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 255)
    private String subtitle;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    @Getter
    @Setter
    private Boolean pinned = false;

    @PrePersist
    private void prePersist(){
        if( created == null){
            created = new Date();
        }
    }
}
