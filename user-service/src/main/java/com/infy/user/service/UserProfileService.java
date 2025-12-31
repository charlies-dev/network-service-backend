package com.infy.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.response.UserProfileResponseDTO;

public interface UserProfileService {
    Long addUserProfile( UserProfileRequestDTO requestDTO);

    void updateUserProfileDetails(Long profileId, UserProfileRequestDTO requestDTO);
 String updateProfileImage(Long userId, MultipartFile profileImage);
    void removeUserProfile(Long profileId);

    UserProfileResponseDTO getUserProfileDetailByUserId(Long userId);

    UserProfileResponseDTO getUserProfileDetailById(Long profileId);
}
