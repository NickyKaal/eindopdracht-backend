package org.nickykaal.backendeindopdracht.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.nickykaal.backendeindopdracht.models.ProfilePicture;
import org.springframework.web.multipart.MultipartFile;

public class ProfilePictureDto {

    @Getter
    @Setter
    public MultipartFile file;
}



