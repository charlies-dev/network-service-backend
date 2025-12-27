package com.infy.user.service;

import com.infy.user.dto.request.UserExperienceRequestDTO;
import com.infy.user.dto.request.UserExperienceUpdateDTO;
import com.infy.user.dto.response.UserExperienceResponseDTO;
import java.util.List;

public interface UserExperienceService {

    Long addUserExperience(Long userId, UserExperienceRequestDTO requestDTO);

    List<Long> addUserExperiences(Long userId, List<UserExperienceRequestDTO> requestDTOs);

    void updateUserExperienceDetails(Long experienceId, UserExperienceUpdateDTO requestDTO);

    void removeUserExperience(Long experienceId);

    List<UserExperienceResponseDTO> getUserExperienceDetailByUserId(Long userId);

    UserExperienceResponseDTO getUserExperienceDetailById(Long experienceId);
}
