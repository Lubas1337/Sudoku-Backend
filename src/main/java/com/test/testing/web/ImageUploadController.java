package com.test.testing.web;

import com.test.testing.domain.payload.response.MessageResponse;
import com.test.testing.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        imageUploadService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }
    @GetMapping("/profileImage")
    public ResponseEntity<byte[]> getUserImage(Principal principal) {
        return imageUploadService.getImageToUser(principal);
    }
}
