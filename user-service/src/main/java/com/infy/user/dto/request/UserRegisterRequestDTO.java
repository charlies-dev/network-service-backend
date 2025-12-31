package com.infy.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterRequestDTO {

    @NotBlank(message = "{user.firstName.notblank}")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "{user.firstName.pattern}")
    private String firstName;

    @NotBlank(message = "{user.lastName.notblank}")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "{user.lastName.pattern}")
    private String lastName;

    @NotBlank(message = "{user.emailId.notblank}")
    @Email(message = "{user.emailId.email}")
    private String emailId;

    @NotBlank(message = "{user.password.notblank}")
    @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,16}$",
      message = "{user.password.pattern}"
    )
    private String password;

    @NotBlank(message = "{user.mobileNo.notblank}")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "{user.mobileNo.pattern}")
    private String mobileNo;
}
