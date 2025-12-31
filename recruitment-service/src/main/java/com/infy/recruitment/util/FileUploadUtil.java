package com.infy.recruitment.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.infy.recruitment.exception.InfyLinkedInException;

@Component
public class FileUploadUtil {

    private static final String UPLOAD_DIR = "uploads/";

    public String uploadPdf(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new InfyLinkedInException("Only PDF files are allowed");
        }

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, filename);

            Files.copy(file.getInputStream(), filePath);

            return "/uploads/" + filename;

        } catch (IOException e) {
            throw new InfyLinkedInException("File upload failed");
        }
    }
}
