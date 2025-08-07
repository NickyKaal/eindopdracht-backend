package org.nickykaal.backendeindopdracht.services;

import ch.qos.logback.core.util.FileUtil;
import jakarta.transaction.Transactional;
import org.nickykaal.backendeindopdracht.exceptions.ResourceNotFoundException;
import org.nickykaal.backendeindopdracht.models.Profile;
import org.nickykaal.backendeindopdracht.models.ProfilePicture;
import org.nickykaal.backendeindopdracht.repositories.ProfilePictureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProfilePictureService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    public final ProfilePictureRepository repo;

    public ProfilePictureService(@Value("${my.upload_location.profile}") String fileStorageLocation, ProfilePictureRepository repo) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.repo = repo;

        Files.createDirectories(fileStoragePath);
    }

    public ProfilePicture storePicture(MultipartFile file, String url) throws IOException {


        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String contentType = file.getContentType();
        String uuid = UUID.randomUUID().toString();
        String fileName = StringUtils.cleanPath(uuid);

        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ProfilePicture picture = new ProfilePicture(originalFileName, url, contentType,  fileName);

        return repo.save(picture);

    }

    public Resource getPictureUrlResource(String fileName) throws IOException {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource = new UrlResource(path.toUri());

        if( !resource.exists() ) {
            throw new ResourceNotFoundException(fileName+" doesn't exist");
        }

        if( !resource.isReadable() ){
           throw new IOException(fileName+" cannot be read");
        }

        return resource;
    }

    public void updateProfilePicture(MultipartFile file, ProfilePicture profilePicture) throws IOException {

        String contentType = file.getContentType();
        if ( contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
        }

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(profilePicture.getFileName());

        Resource resource = new UrlResource(path.toUri());

        if( !resource.exists() ) {
            throw new ResourceNotFoundException("doesn't exist");
        }

        if( !resource.isReadable() ){
            throw new IOException("cannot be read");
        }

        Files.copy( file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());

        profilePicture.setTitle(originalFileName);
        profilePicture.setContentType(contentType);

        repo.save(profilePicture);
    }

    public void deleteProfilePicture(ProfilePicture profilePicture) throws IOException {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(profilePicture.getFileName());

        Resource resource = new UrlResource(path.toUri());

        if( resource.exists() ) {
            Files.delete(path);
        }

        repo.delete(profilePicture);

    }
}
