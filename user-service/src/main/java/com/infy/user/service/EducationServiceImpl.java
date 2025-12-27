package com.infy.user.service;

import com.infy.user.dto.request.EducationRequestDTO;
import com.infy.user.dto.request.EducationUpdateDTO;
import com.infy.user.dto.response.EducationResponseDTO;
import com.infy.user.entity.Education;
import com.infy.user.entity.User;
import com.infy.user.exception.InfyLinkedInException;
import com.infy.user.repository.EducationRepository;
import com.infy.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /* ================= ADD EDUCATION ================= */

    @Override
    @Transactional
    public Long addUserEducation(Long userId, EducationRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        Education education = modelMapper.map(requestDTO, Education.class);
        education.setUser(user);
        education.setCreatedAt(LocalDateTime.now());

        Education savedEducation = educationRepository.save(education);
        return savedEducation.getId();
    }

    @Override
    @Transactional
    public List<Long> addUserEducations(
            Long userId,
            List<EducationRequestDTO> requestDTOs) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        if (requestDTOs == null || requestDTOs.isEmpty()) {
            throw new InfyLinkedInException("Education list cannot be empty");
        }

        List<Education> educations = requestDTOs.stream()
                .map(dto -> {
                    Education education = modelMapper.map(dto, Education.class);
                    education.setUser(user);
                    education.setCreatedAt(LocalDateTime.now());
                    return education;
                })
                .toList();

        List<Education> savedEducations = educationRepository.saveAll(educations);

        return savedEducations.stream()
                .map(Education::getId)
                .toList();
    }

    /* ================= UPDATE EDUCATION ================= */

    @Override
    @Transactional
    public void updateEducationDetails(Long educationId, EducationUpdateDTO requestDTO) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new InfyLinkedInException("Education record not found"));

        if (requestDTO.getInstitution() != null) {
            education.setInstitution(requestDTO.getInstitution());
        }
        if (requestDTO.getDegree() != null) {
            education.setDegree(requestDTO.getDegree());
        }
        if (requestDTO.getFieldOfStudy() != null) {
            education.setFieldOfStudy(requestDTO.getFieldOfStudy());
        }
        if (requestDTO.getStartYear() != null) {
            education.setStartYear(requestDTO.getStartYear());
        }
        if (requestDTO.getEndYear() != null) {
            education.setEndYear(requestDTO.getEndYear());
        }

        education.setUpdatedAt(LocalDateTime.now());
    }

    /* ================= REMOVE EDUCATION ================= */

    @Override
    @Transactional
    public void removeEducation(Long educationId) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new InfyLinkedInException("Education record not found"));

        educationRepository.delete(education);
    }

    /* ================= GET EDUCATION BY USER ================= */

    @Override
    public List<EducationResponseDTO> getEducationDetailByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new InfyLinkedInException("User not found");
        }

        List<Education> educations = educationRepository.findByUserId(userId);

        if (educations.isEmpty()) {
            throw new InfyLinkedInException("No education details found");
        }

        return educations.stream()
                .map(edu -> modelMapper.map(edu, EducationResponseDTO.class))
                .toList();
    }

    /* ================= GET EDUCATION BY ID ================= */

    @Override
    public EducationResponseDTO getEducationDetailById(Long educationId) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new InfyLinkedInException("Education record not found"));

        return modelMapper.map(education, EducationResponseDTO.class);
    }
}
