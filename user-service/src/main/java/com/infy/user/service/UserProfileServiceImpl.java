package com.infy.user.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.request.UserProfileResponseDTO;
import com.infy.user.entity.User;
import com.infy.user.entity.UserProfile;
import com.infy.user.exception.InfyLinkedInException;
import com.infy.user.repository.UserProfileRepository;
import com.infy.user.repository.UserRepository;
import com.infy.user.util.UserProfileImageUploadUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final UserProfileImageUploadUtil imageUploadUtil;

    /* ================= ADD PROFILE ================= */

    @Override
    @Transactional
    public Long addUserProfile( UserProfileRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() ->
                        new InfyLinkedInException("User not found"));

        if (userProfileRepository.existsByUserId(requestDTO.getUserId())) {
            throw new InfyLinkedInException("User profile already exists");
        }

        UserProfile profile = modelMapper.map(requestDTO, UserProfile.class);
        profile.setUser(user);
        profile.setCreatedAt(LocalDateTime.now());

        String imageUrl =
                imageUploadUtil.uploadProfileImage(requestDTO.getProfilePhoto());
        profile.setProfilePhotoUrl(imageUrl);

        return userProfileRepository.save(profile).getId();
    }

    /* ================= UPDATE PROFILE ================= */

    @Override
    @Transactional
    public void updateUserProfileDetails(
            Long profileId,
            UserProfileRequestDTO requestDTO) {

        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() ->
                        new InfyLinkedInException("User profile not found"));

        if (requestDTO.getHeadline() != null)
            profile.setHeadline(requestDTO.getHeadline());

        if (requestDTO.getSummary() != null)
            profile.setSummary(requestDTO.getSummary());

        if (requestDTO.getCurrentJobTitle() != null)
            profile.setCurrentJobTitle(requestDTO.getCurrentJobTitle());

        if (requestDTO.getAspirations() != null)
            profile.setAspirations(requestDTO.getAspirations());

        if (requestDTO.getIndustry() != null)
            profile.setIndustry(requestDTO.getIndustry());

        if (requestDTO.getLocation() != null)
            profile.setLocation(requestDTO.getLocation());

        if (requestDTO.getCountry() != null)
            profile.setCountry(requestDTO.getCountry());

       if (requestDTO.getProfilePhoto() != null) {
            String imageUrl =
                    imageUploadUtil.uploadProfileImage(requestDTO.getProfilePhoto());
            profile.setProfilePhotoUrl(imageUrl);
        }
        if (requestDTO.getProfileCompleted() != null)
            profile.setProfileCompleted(requestDTO.getProfileCompleted());

        profile.setUpdatedAt(LocalDateTime.now());
    }

    /* ================= REMOVE PROFILE ================= */

    @Override
    @Transactional
    public void removeUserProfile(Long profileId) {

        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() ->
                        new InfyLinkedInException("User profile not found"));

        userProfileRepository.delete(profile);
    }

    /* ================= GET BY USER ================= */

    @Override
    public UserProfileResponseDTO getUserProfileDetailByUserId(Long userId) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new InfyLinkedInException("User profile not found"));

        return modelMapper.map(profile, UserProfileResponseDTO.class);
    }

    /* ================= GET BY ID ================= */

    @Override
    public UserProfileResponseDTO getUserProfileDetailById(Long profileId) {

        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() ->
                        new InfyLinkedInException("User profile not found"));

        return modelMapper.map(profile, UserProfileResponseDTO.class);
    }
}
