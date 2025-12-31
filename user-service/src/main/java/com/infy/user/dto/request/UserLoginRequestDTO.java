package com.infy.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDTO {

    @NotBlank(message = "{user.login.emailId.notblank}")
    @Email(message = "{user.login.emailId.email}")
    private String emailId;

    @NotBlank(message = "{user.login.password.notblank}")
    private String password;
}