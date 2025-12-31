package com.infy.user.service;

import java.util.List;

import com.infy.user.dto.response.UserResponseDTO;
import com.infy.user.dto.response.UserSkillResponseDTO;

public interface UserSkillService {

    Long addUserSkill(Long userId, String requestDTO);

    List<Long> addUserSkills(Long userId, List<String> requestDTOs);

    void updateUserSkill(Long userSkillId, String requestDTO);

    List<UserSkillResponseDTO> getUserSkillDetailByUserId(Long userId);

    List<UserResponseDTO> getUsersBySkillId(Long skillId);
}
