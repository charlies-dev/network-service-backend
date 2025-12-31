package com.infy.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.user.dto.EducationDTO;
import com.infy.user.dto.UserExperienceDTO;
import com.infy.user.dto.UserProfileDTO;
import com.infy.user.dto.UserSkillDTO;
import com.infy.user.dto.request.UpdateUserRequestDTO;
import com.infy.user.dto.request.UserDetailsRequestDTO;
import com.infy.user.dto.request.UserLoginRequestDTO;
import com.infy.user.dto.request.UserRegisterRequestDTO;
import com.infy.user.dto.response.UserResponseDTO;
import com.infy.user.entity.Education;
import com.infy.user.entity.Skill;
import com.infy.user.entity.User;
import com.infy.user.entity.UserExperience;
import com.infy.user.entity.UserProfile;
import com.infy.user.entity.UserSkill;
import com.infy.user.exception.InfyLinkedInException;
import com.infy.user.repository.SkillRepository;
import com.infy.user.repository.UserRepository;
import com.infy.user.util.UserProfileImageUploadUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final SkillRepository skillRepository;
    private final UserProfileImageUploadUtil imageUploadUtil;

    @Override
    public Long registerUser(UserRegisterRequestDTO requestDTO) {
        if (userRepository.existsByEmailId(requestDTO.getEmailId())) {
            throw new InfyLinkedInException("Email already registered");
        }
        if (userRepository.existsByMobileNo(requestDTO.getMobileNo())) {
            throw new InfyLinkedInException("Mobile no already registered");
        }

        User user = modelMapper.map(requestDTO, User.class);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setCreatedAt(LocalDate.now());

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserResponseDTO loginUser(UserLoginRequestDTO requestDTO) {
        User user = userRepository.findByEmailId(requestDTO.getEmailId())
                .orElseThrow(() -> new InfyLinkedInException("Invalid email or password"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new InfyLinkedInException("Invalid email or password");
        }

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public void updateUser(Long userId, UpdateUserRequestDTO requestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        if (!user.getEmailId().equals(requestDTO.getEmailId())
                && userRepository.existsByEmailId(requestDTO.getEmailId())) {
            throw new InfyLinkedInException("Email already in use");
        }

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmailId(requestDTO.getEmailId());
        user.setMobileNo(requestDTO.getMobileNo());
        user.setUpdatedAt(LocalDate.now());
    }

    @Override
    public void updateVerifyUserStatus(Long userId, Boolean isVerified) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        user.setIsVerified(isVerified);
    }

    @Override
    public void forgotPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));
        
        return getUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers(Integer limit) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new InfyLinkedInException("No users found");
        }

        List<UserResponseDTO> userDTOs = users.stream()
                .map(this::getUserResponseDTO)
                .toList();

        if (limit != null && limit > 0) {
            return userDTOs.stream()
                    .limit(limit)
                    .toList();
        }

        return userDTOs;
    }

    @Override
    public List<UserResponseDTO> searchUsersByName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new InfyLinkedInException("Please provide a valid name");
        }

        List<User> users = userRepository.searchByName(name.trim());

        if (users.isEmpty()) {
            throw new InfyLinkedInException("No users found with given name");
        }

        return users.stream()
                .map(this::getUserResponseDTO)
                .toList();
    }

    public String addUserDetails(Long userId, UserDetailsRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        if (requestDTO.getProfile() != null) {

            UserProfile profile = modelMapper.map(
                    requestDTO.getProfile(),
                    UserProfile.class);
           
            profile.setUser(user);
            profile.setCreatedAt(LocalDateTime.now());

            user.setProfile(profile);
        }

        if (requestDTO.getEducations() != null &&
                !requestDTO.getEducations().isEmpty()) {

            List<Education> educations = requestDTO.getEducations()
                    .stream()
                    .map(dto -> {
                        Education edu = modelMapper.map(dto, Education.class);
                        edu.setUser(user);
                        edu.setCreatedAt(LocalDateTime.now());
                        return edu;
                    })
                    .toList();

            user.getEducations().clear();
            user.getEducations().addAll(educations);
        }

        if (requestDTO.getExperiences() != null &&
                !requestDTO.getExperiences().isEmpty()) {

            List<UserExperience> experiences = requestDTO.getExperiences()
                    .stream()
                    .map(dto -> {
                        UserExperience exp = modelMapper.map(dto, UserExperience.class);
                        exp.setUser(user);
                        exp.setCreatedAt(LocalDateTime.now());
                        return exp;
                    })
                    .toList();

            user.getExperiences().clear();
            user.getExperiences().addAll(experiences);
        }

        if (requestDTO.getUserSkills() != null &&
                !requestDTO.getUserSkills().isEmpty()) {

            List<UserSkill> skills = requestDTO.getUserSkills()
                    .stream()
                    .map(dto -> {
                        Skill skill = skillRepository.findByName(dto)
                                .orElseGet(() -> {
                                    Skill newSkill = new Skill();
                                    newSkill.setName(dto);
                                    return skillRepository.save(newSkill);
                                });

                        return UserSkill.builder()
                                .user(user)
                                .skill(skill)
                                .build();
                    })
                    .toList();

            user.getUserSkills().clear();
            user.getUserSkills().addAll(skills);
        }

        user.setUpdatedAt(LocalDate.now());

        userRepository.save(user);

        return "User details added successfully";
    }

    @Override
    public List<UserResponseDTO> getUserByIds(List<Long> userIds) {

        List<User> users = userRepository.findAllById(userIds);

        if (users.size() != userIds.size()) {
            throw new InfyLinkedInException("One or more users not found");
        }

        return users.stream()
                .map(this::getUserResponseDTO)
                .toList();
    }

    private UserResponseDTO getUserResponseDTO(User user){
        UserResponseDTO responseDTO = modelMapper.map(user, UserResponseDTO.class);

        if (user.getEducations() != null) {
            List<EducationDTO> educationDTO = user.getEducations().stream()
                    .map(entity -> {
                        EducationDTO dto = modelMapper.map(entity, EducationDTO.class);

                        return dto;
                    })
                    .toList();
            responseDTO.setEducations(educationDTO);

        }
        if (user.getExperiences() != null) {
            List<UserExperienceDTO> experienceDTO = user.getExperiences().stream()
                    .map(entity -> {
                        UserExperienceDTO dto = modelMapper.map(entity, UserExperienceDTO.class);

                        return dto;
                    })
                    .toList();
            responseDTO.setExperiences(experienceDTO);

        }
        if (user.getUserSkills() != null) {
            List<UserSkillDTO> skillDTO = user.getUserSkills().stream()
                    .map(entity -> {
                        UserSkillDTO dto = new UserSkillDTO();
                        dto.setId(entity.getSkill().getId());
                        dto.setSkillName(entity.getSkill().getName());
                        return dto;
                    })
                    .toList();
            responseDTO.setUserSkills(skillDTO);

        }
        if (user.getProfile() != null) {
            UserProfileDTO profileDTO = modelMapper.map(user.getProfile(), UserProfileDTO.class);
            responseDTO.setProfile(profileDTO);

        }

        return responseDTO;
    }
}
