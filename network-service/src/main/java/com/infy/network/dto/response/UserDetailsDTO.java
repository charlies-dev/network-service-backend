package com.infy.network.dto.response;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String headline;
    private String profilePhotoUrl;
}
