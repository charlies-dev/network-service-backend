package com.infy.user.service;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.user.dto.request.UserSkillRequestDTO;
import com.infy.user.dto.response.UserResponseDTO;
import com.infy.user.dto.response.UserSkillResponseDTO;
import com.infy.user.entity.Skill;
import com.infy.user.entity.User;
import com.infy.user.entity.UserSkill;
import com.infy.user.exception.InfyLinkedInException;
import com.infy.user.repository.SkillRepository;
import com.infy.user.repository.UserRepository;
import com.infy.user.repository.UserSkillRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSkillServiceImpl implements UserSkillService {

        private final UserRepository userRepository;
        private final SkillRepository skillRepository;
        private final UserSkillRepository userSkillRepository;
        private final ModelMapper modelMapper;
        /* ================= ADD SINGLE SKILL ================= */

        @Override
        @Transactional
        public Long addUserSkill(Long userId, UserSkillRequestDTO requestDTO) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new InfyLinkedInException("User not found"));

                Skill skill = skillRepository
                                .findByNameIgnoreCase(requestDTO.getSkillName())
                                .orElseGet(() -> skillRepository.save(
                                                Skill.builder()
                                                                .name(requestDTO.getSkillName())
                                                                .build()));

                if (userSkillRepository.existsByUserIdAndSkillId(userId, skill.getId())) {
                        throw new InfyLinkedInException("Skill already assigned to user");
                }

                UserSkill userSkill = UserSkill.builder()
                                .user(user)
                                .skill(skill)
                                .build();

                return userSkillRepository.save(userSkill).getId();
        }

        /* ================= ADD MULTIPLE SKILLS ================= */

        @Override
        @Transactional
        public List<Long> addUserSkills(
                        Long userId,
                        List<UserSkillRequestDTO> requestDTOs) {

                if (requestDTOs == null || requestDTOs.isEmpty()) {
                        throw new InfyLinkedInException("Skill list cannot be empty");
                }

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new InfyLinkedInException("User not found"));

                List<UserSkill> userSkills = requestDTOs.stream()
                                .map(dto -> {

                                        Skill skill = skillRepository
                                                        .findByNameIgnoreCase(dto.getSkillName())
                                                        .orElseGet(() -> skillRepository.save(
                                                                        Skill.builder()
                                                                                        .name(dto.getSkillName())
                                                                                        .build()));

                                        if (userSkillRepository.existsByUserIdAndSkillId(userId, skill.getId())) {
                                                return null;
                                        }

                                        return UserSkill.builder()
                                                        .user(user)
                                                        .skill(skill)
                                                        .build();
                                })
                                .filter(Objects::nonNull)
                                .toList();

                return userSkillRepository.saveAll(userSkills)
                                .stream()
                                .map(UserSkill::getId)
                                .toList();
        }

        /* ================= UPDATE USER SKILL ================= */

        @Override
        @Transactional
        public void updateUserSkill(Long userSkillId, UserSkillRequestDTO requestDTO) {

                UserSkill userSkill = userSkillRepository.findById(userSkillId)
                                .orElseThrow(() -> new InfyLinkedInException("User skill not found"));

                Skill skill = skillRepository
                                .findByNameIgnoreCase(requestDTO.getSkillName())
                                .orElseGet(() -> skillRepository.save(
                                                Skill.builder()
                                                                .name(requestDTO.getSkillName())
                                                                .build()));

                userSkill.setSkill(skill);
        }

        /* ================= REMOVE SKILL ================= */

        @Override
        @Transactional
        public void removeUserSkill(Long userSkillId) {

                UserSkill userSkill = userSkillRepository.findById(userSkillId)
                                .orElseThrow(() -> new InfyLinkedInException("User skill not found"));

                userSkillRepository.delete(userSkill);
        }

        /* ================= GET BY USER ================= */

        @Override
        public List<UserSkillResponseDTO> getUserSkillDetailByUserId(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new InfyLinkedInException("User not found");
                }

                List<UserSkill> skills = userSkillRepository.findByUserId(userId);

                if (skills.isEmpty()) {
                        throw new InfyLinkedInException("No skills found for user");
                }

                return skills.stream()
                                .map(us -> {
                                        UserSkillResponseDTO dto = new UserSkillResponseDTO();
                                        dto.setId(us.getId());
                                        dto.setSkillId(us.getSkill().getId());
                                        dto.setSkillName(us.getSkill().getName());
                                        return dto;
                                })
                                .toList();
        }

        @Override
        public List<UserResponseDTO> getUsersBySkillId(Long skillId) {

                if (skillId == null) {
                        throw new InfyLinkedInException("Skill id is required");
                }

                List<UserSkill> userSkills = userSkillRepository.findBySkillId(skillId);

                if (userSkills.isEmpty()) {
                        throw new InfyLinkedInException("No users found for given skill");
                }

                return userSkills.stream()
                                .map(UserSkill::getUser)
                                .distinct()
                                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                                .toList();
        }
}
