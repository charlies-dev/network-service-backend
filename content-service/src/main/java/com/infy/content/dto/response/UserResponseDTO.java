package com.infy.content.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNo;
    private Boolean isVerified;
    private Boolean isActive;

    private UserProfileDTO profile;

   
}
