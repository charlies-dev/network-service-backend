package com.infy.user.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.user.dto.request.UserProfileRequestDTO;
import com.infy.user.dto.request.UserProfileResponseDTO;
import com.infy.user.service.UserProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user-profile")
@RequiredArgsConstructor
public class UserProfileController {
private final UserProfileService userProfileService;

    /* ================= ADD PROFILE ================= */

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> addProfile(
            @Valid @ModelAttribute UserProfileRequestDTO dto) {

        return ResponseEntity.ok(
                userProfileService.addUserProfile(dto)
        );
    }

    /* ================= UPDATE PROFILE ================= */

    @PutMapping(
            value = "/{profileId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> updateProfile(
            @PathVariable Long profileId,
            @ModelAttribute UserProfileRequestDTO dto) {

        userProfileService.updateUserProfileDetails(profileId, dto);
        return ResponseEntity.noContent().build();
    }

    /* ================= GET ================= */

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

    /* ================= DELETE ================= */

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable Long profileId) {

        userProfileService.removeUserProfile(profileId);
        return ResponseEntity.noContent().build();
    }
}
