package org.nickykaal.backendeindopdracht.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table( name="announcements")
public class Announcement {
    @Id
    @GeneratedValue
    @Getter
    private Long id_announcement;

    @OneToMany(mappedBy="announcement")
    private Set<AnnouncementChatMessage> chatMessages;

    @Getter
    @Setter
    private Date created;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 255)
    private String title;

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
