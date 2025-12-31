package com.infy.user.service;

import java.util.List;

import com.infy.user.dto.request.UserExperienceRequestDTO;
import com.infy.user.dto.response.UserExperienceResponseDTO;

public interface UserExperienceService {

    Long addUserExperience(Long userId, UserExperienceRequestDTO requestDTO);

    List<Long> addUserExperiences(Long userId, List<UserExperienceRequestDTO> requestDTOs);

    void updateUserExperienceDetails(Long experienceId, UserExperienceRequestDTO requestDTO);

    void removeUserExperience(Long experienceId);

    List<UserExperienceResponseDTO> getUserExperienceDetailByUserId(Long userId);

    UserExperienceResponseDTO getUserExperienceDetailById(Long experienceId);
}
