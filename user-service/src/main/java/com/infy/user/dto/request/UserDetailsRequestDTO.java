package com.infy.user.dto.request;

import java.util.List;

import com.infy.user.dto.EducationDTO;
import com.infy.user.dto.UserExperienceDTO;
import com.infy.user.dto.UserProfileDTO;
import com.infy.user.dto.UserSkillDTO;

import lombok.Data;

@Data
public class UserDetailsRequestDTO {

    private UserProfileDTO profile;

    private List<EducationDTO> educations;

    private List<UserExperienceDTO> experiences;

    private List<UserSkillDTO> userSkills;
}
