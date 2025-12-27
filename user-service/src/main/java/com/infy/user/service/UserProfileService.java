package com.infy.user.service;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.request.UserProfileResponseDTO;
import com.infy.user.dto.request.UserProfileUpdateDTO;

public interface UserProfileService {
    Long addUserProfile(Long userId, UserProfileRequestDTO requestDTO);

    void updateUserProfileDetails(Long profileId, UserProfileUpdateDTO requestDTO);

    void removeUserProfile(Long profileId);

    UserProfileResponseDTO getUserProfileDetailByUserId(Long userId);

    UserProfileResponseDTO getUserProfileDetailById(Long profileId);
}
