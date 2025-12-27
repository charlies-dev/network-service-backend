package com.infy.user.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileDTO {
    @NotBlank
    private String headline;

    private String summary;

    private String currentJobTitle;

    private String aspirations;

    private String industry;

    private String location;

    private String country;

    private String profilePhotoUrl;
}
