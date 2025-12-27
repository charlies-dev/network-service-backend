package com.infy.user.service;

import com.infy.user.dto.request.UserSkillRequestDTO;
import com.infy.user.dto.response.UserResponseDTO;
import com.infy.user.dto.response.UserSkillResponseDTO;
import java.util.List;

public interface UserSkillService {

    Long addUserSkill(Long userId, UserSkillRequestDTO requestDTO);

    List<Long> addUserSkills(Long userId, List<UserSkillRequestDTO> requestDTOs);

    void updateUserSkill(Long userSkillId, UserSkillRequestDTO requestDTO);

    void removeUserSkill(Long userSkillId);

    List<UserSkillResponseDTO> getUserSkillDetailByUserId(Long userId);

    List<UserResponseDTO> getUsersBySkillId(Long skillId);
}
