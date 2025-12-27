package com.infy.user.service;

import com.infy.user.dto.request.UserExperienceRequestDTO;
import com.infy.user.dto.request.UserExperienceUpdateDTO;
import com.infy.user.dto.response.UserExperienceResponseDTO;
import com.infy.user.entity.User;
import com.infy.user.entity.UserExperience;
import com.infy.user.exception.InfyLinkedInException;
import com.infy.user.repository.UserExperienceRepository;
import com.infy.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserExperienceServiceImpl implements UserExperienceService {

    private final UserExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /* ================= ADD SINGLE EXPERIENCE ================= */

    @Override
    @Transactional
    public Long addUserExperience(Long userId, UserExperienceRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new InfyLinkedInException("User not found"));

        UserExperience experience =
                modelMapper.map(requestDTO, UserExperience.class);

        experience.setUser(user);
        experience.setCreatedAt(LocalDateTime.now());

        return experienceRepository.save(experience).getId();
    }

    /* ================= ADD MULTIPLE EXPERIENCES ================= */

    @Override
    @Transactional
    public List<Long> addUserExperiences(
            Long userId,
            List<UserExperienceRequestDTO> requestDTOs) {

        if (requestDTOs == null || requestDTOs.isEmpty()) {
            throw new InfyLinkedInException("Experience list cannot be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new InfyLinkedInException("User not found"));

        List<UserExperience> experiences = requestDTOs.stream()
                .map(dto -> {
                    UserExperience exp =
                            modelMapper.map(dto, UserExperience.class);
                    exp.setUser(user);
                    exp.setCreatedAt(LocalDateTime.now());
                    return exp;
                })
                .toList();

        return experienceRepository.saveAll(experiences)
                .stream()
                .map(UserExperience::getId)
                .toList();
    }

    /* ================= UPDATE EXPERIENCE ================= */

    @Override
    @Transactional
    public void updateUserExperienceDetails(
            Long experienceId,
            UserExperienceUpdateDTO requestDTO) {

        UserExperience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Experience not found"));

        if (requestDTO.getCompanyName() != null)
            experience.setCompanyName(requestDTO.getCompanyName());

        if (requestDTO.getJobTitle() != null)
            experience.setJobTitle(requestDTO.getJobTitle());

        if (requestDTO.getEmploymentType() != null)
            experience.setEmploymentType(requestDTO.getEmploymentType());

        if (requestDTO.getLocation() != null)
            experience.setLocation(requestDTO.getLocation());

        if (requestDTO.getStartDate() != null)
            experience.setStartDate(requestDTO.getStartDate());

        if (requestDTO.getEndDate() != null)
            experience.setEndDate(requestDTO.getEndDate());

        if (requestDTO.getIsCurrent() != null)
            experience.setIsCurrent(requestDTO.getIsCurrent());

        if (requestDTO.getDescription() != null)
            experience.setDescription(requestDTO.getDescription());

        experience.setUpdatedAt(LocalDateTime.now());
    }

    /* ================= REMOVE EXPERIENCE ================= */

    @Override
    @Transactional
    public void removeUserExperience(Long experienceId) {

        UserExperience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Experience not found"));

        experienceRepository.delete(experience);
    }

    /* ================= GET BY USER ================= */

    @Override
    public List<UserExperienceResponseDTO> getUserExperienceDetailByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new InfyLinkedInException("User not found");
        }

        List<UserExperience> experiences =
                experienceRepository.findByUserId(userId);

        if (experiences.isEmpty()) {
            throw new InfyLinkedInException("No experience details found");
        }

        return experiences.stream()
                .map(exp -> modelMapper.map(exp, UserExperienceResponseDTO.class))
                .toList();
    }

    /* ================= GET BY ID ================= */

    @Override
    public UserExperienceResponseDTO getUserExperienceDetailById(Long experienceId) {

        UserExperience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Experience not found"));

        return modelMapper.map(experience, UserExperienceResponseDTO.class);
    }
}
