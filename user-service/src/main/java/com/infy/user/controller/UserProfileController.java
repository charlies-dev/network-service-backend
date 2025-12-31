package com.infy.user.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.response.UserProfileResponseDTO;
import com.infy.user.service.UserProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user-profile")
@RequiredArgsConstructor
public class UserProfileController {
private final UserProfileService userProfileService;

    @PostMapping()
    public ResponseEntity<Long> addProfile(
            @Valid @RequestBody UserProfileRequestDTO dto) {

        return ResponseEntity.ok(
                userProfileService.addUserProfile(dto)
        );
    }

    @PutMapping(
            value = "/{profileId}"
    )
    public ResponseEntity<Void> updateProfile(
            @PathVariable Long profileId,
            @RequestBody UserProfileRequestDTO dto) {

        userProfileService.updateUserProfileDetails(profileId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileResponseDTO> getByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userProfileService.getUserProfileDetailByUserId(userId)
        );
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<UserProfileResponseDTO> getById(
            @PathVariable Long profileId) {

        return ResponseEntity.ok(
                userProfileService.getUserProfileDetailById(profileId)
        );
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable Long profileId) {

        userProfileService.removeUserProfile(profileId);
        return ResponseEntity.noContent().build();
    }

     @PutMapping(
            value = "/{userId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long userId,
            @RequestPart("profileImage") MultipartFile profileImage) {

        String imageUrl =
                userProfileService.updateProfileImage(userId, profileImage);

        return ResponseEntity.ok(imageUrl);
    }
}
