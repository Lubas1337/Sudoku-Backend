package com.test.testing.services;

import com.test.testing.domain.Entity.ImageModel;
import com.test.testing.domain.Entity.UserEntity;
import com.test.testing.domain.repository.ImageRepository;
import com.test.testing.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Service
public class ImageUploadService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageUploadService.class);
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        UserEntity user = getUserByPrincipal(principal);
        LOG.info("Uploading image profile to User {}", user.getUsername());
        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }
        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setImageBytes(file.getBytes());
        imageModel.setFileType(getFileType(Objects.requireNonNull(file.getOriginalFilename())));
        imageModel.setName(file.getOriginalFilename());
        imageRepository.save(imageModel);
    }

    private String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }


    @Transactional
    public ResponseEntity<byte[]> getImageToUser(Principal principal) {
        UserEntity user = getUserByPrincipal(principal);
        ImageModel imageModel = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            byte[] imageBytes = imageModel.getImageBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }








    private UserEntity getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }
}
