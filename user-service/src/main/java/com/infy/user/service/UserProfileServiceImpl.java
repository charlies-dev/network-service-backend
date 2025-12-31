package com.infy.user.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.response.UserProfileResponseDTO;
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

    @Override
    @Transactional
    public Long addUserProfile(UserProfileRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new InfyLinkedInException("User not found"));

        if (userProfileRepository.existsByUserId(requestDTO.getUserId())) {
            throw new InfyLinkedInException("User profile already exists");
        }

        UserProfile profile = modelMapper.map(requestDTO, UserProfile.class);
        profile.setUser(user);
        profile.setCreatedAt(LocalDateTime.now());

        return userProfileRepository.save(profile).getId();
    }

    @Override
    @Transactional
    public void updateUserProfileDetails(
            Long profileId,
            UserProfileRequestDTO requestDTO) {

        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() -> new InfyLinkedInException("User profile not found"));

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

        if (requestDTO.getProfileCompleted() != null)
            profile.setProfileCompleted(requestDTO.getProfileCompleted());

        profile.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void removeUserProfile(Long profileId) {

        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() -> new InfyLinkedInException("User profile not found"));

        userProfileRepository.delete(profile);
    }

    @Override
    public UserProfileResponseDTO getUserProfileDetailByUserId(Long userId) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new InfyLinkedInException("User profile not found"));

        return modelMapper.map(profile, UserProfileResponseDTO.class);
    }

    @Override
    public UserProfileResponseDTO getUserProfileDetailById(Long profileId) {

        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() -> new InfyLinkedInException("User profile not found"));

        return modelMapper.map(profile, UserProfileResponseDTO.class);
    }

    @Override
    @Transactional
    public String updateProfileImage(Long userId, MultipartFile profileImage) {

        if (profileImage == null || profileImage.isEmpty()) {
            throw new InfyLinkedInException("Profile image is required");
        }

        if (!profileImage.getContentType().startsWith("image/")) {
            throw new InfyLinkedInException("Only image files are allowed");
        }

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new InfyLinkedInException("User profile not found"));

        String imageUrl = imageUploadUtil.uploadProfileImage(profileImage);

        profile.setProfilePhotoUrl(imageUrl);
        profile.setUpdatedAt(LocalDateTime.now());

        return imageUrl;
    }

}
