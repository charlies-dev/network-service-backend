package com.infy.user.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.infy.user.exception.InfyLinkedInException;

@Component
public class UserProfileImageUploadUtil {

    private static final String UPLOAD_DIR = "uploads/user-profile/";

    public String uploadProfileImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new InfyLinkedInException("Only image files are allowed");
        }

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String fileName =
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            Files.copy(file.getInputStream(), filePath);

            return "/uploads/user-profile/" + fileName;

        } catch (IOException ex) {
            throw new InfyLinkedInException("Profile image upload failed");
        }
    }
}
