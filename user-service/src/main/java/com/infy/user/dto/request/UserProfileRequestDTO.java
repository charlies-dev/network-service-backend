package com.infy.user.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UserProfileRequestDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String headline;

    private String summary;
    private String currentJobTitle;
    private String aspirations;
    private String industry;
    private String location;
    private String country;

    /* Image file */
    private MultipartFile profilePhoto;
    
    private Boolean profileCompleted;
}
