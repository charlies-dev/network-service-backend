package com.infy.user.dto;

import java.time.LocalDate;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String emailId;

    private String password;

    private String mobileNo;

    private Boolean isVerified = false;

    private Boolean isActive = true;

    private LocalDate createdAt;

    private LocalDate updatedAt;


    
}
