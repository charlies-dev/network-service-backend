package com.infy.content.dto.response;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;

    private String profilePhotoUrl;
}
