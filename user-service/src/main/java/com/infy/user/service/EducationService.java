package com.infy.user.service;

import java.util.List;

import com.infy.user.dto.request.EducationRequestDTO;
import com.infy.user.dto.response.EducationResponseDTO;

public interface EducationService {

   Long addUserEducation(Long userId, EducationRequestDTO requestDTO);

    List<Long> addUserEducations(Long userId, List<EducationRequestDTO> requestDTOs);
    void updateEducationDetails(Long educationId, EducationRequestDTO requestDTO);

    void removeEducation(Long educationId);

    List<EducationResponseDTO> getEducationDetailByUserId(Long userId);

    EducationResponseDTO getEducationDetailById(Long educationId);
}
