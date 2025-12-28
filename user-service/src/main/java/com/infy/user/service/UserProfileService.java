package com.infy.user.service;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.request.UserProfileResponseDTO;

public interface UserProfileService {
    Long addUserProfile( UserProfileRequestDTO requestDTO);

    void updateUserProfileDetails(Long profileId, UserProfileRequestDTO requestDTO);

    void removeUserProfile(Long profileId);

    UserProfileResponseDTO getUserProfileDetailByUserId(Long userId);

    UserProfileResponseDTO getUserProfileDetailById(Long profileId);
}
