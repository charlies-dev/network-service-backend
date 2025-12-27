package com.infy.user.dto.response;

import java.util.List;

import com.infy.user.dto.EducationDTO;
import com.infy.user.dto.UserExperienceDTO;
import com.infy.user.dto.UserProfileDTO;
import com.infy.user.dto.UserSkillDTO;

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

    private List<EducationDTO> educations;

    private List<UserExperienceDTO> experiences;

    private List<UserSkillDTO> userSkills;
}
