package com.infy.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String lastName;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String mobileNo;
}

