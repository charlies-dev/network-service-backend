package com.infy.user.dto.response;

import lombok.Data;

@Data
public class UserProfileResponseDTO {

    private Long id;
    private String headline;
    private String summary;
    private String currentJobTitle;
    private String aspirations;
    private String industry;
    private String location;
    private String country;
    private String profilePhotoUrl;
    private Boolean profileCompleted;
}
