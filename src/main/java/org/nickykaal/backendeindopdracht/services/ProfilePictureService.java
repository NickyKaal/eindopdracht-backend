package org.nickykaal.backendeindopdracht.services;

import jakarta.transaction.Transactional;
import org.nickykaal.backendeindopdracht.models.ProfilePicture;
import org.nickykaal.backendeindopdracht.repositories.ProfilePictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfilePictureService {
    
    private final ProfilePictureRepository repo;

    public ProfilePictureService(ProfilePictureRepository repo) {
        this.repo = repo;
    }

    public ProfilePicture storePicture(MultipartFile file, String url) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();

        ProfilePicture picture = new ProfilePicture(originalFileName, contentType, url , bytes);

        return repo.save(picture);
    }
}
