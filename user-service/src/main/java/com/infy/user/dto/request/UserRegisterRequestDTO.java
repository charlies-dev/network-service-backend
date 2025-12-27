package com.infy.user.dto.request;

import java.util.List;

import com.infy.user.dto.EducationDTO;
import com.infy.user.dto.UserExperienceDTO;
import com.infy.user.dto.UserProfileDTO;
import com.infy.user.dto.UserSkillDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$")
    private String lastName;

    @NotBlank
    @Email(message = "Invalid email format")
    private String emailId;

    @NotBlank
    @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,16}$"
    )
    private String password;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String mobileNo;

    // Optional
    private UserProfileDTO profile;
    private List<EducationDTO> educations;
    private List<UserExperienceDTO> experiences;
    private List<UserSkillDTO> userSkills;
}
