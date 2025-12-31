package com.infy.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.user.dto.request.UserExperienceRequestDTO;
import com.infy.user.dto.response.UserExperienceResponseDTO;
import com.infy.user.service.UserExperienceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class UserExperienceController {
    private final UserExperienceService experienceService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Long> addExperience(
            @PathVariable Long userId,
            @Valid @RequestBody UserExperienceRequestDTO dto) {

        return ResponseEntity.ok(
                experienceService.addUserExperience(userId, dto));
    }

    @PostMapping("/user/{userId}/bulk")
    public ResponseEntity<List<Long>> addExperiences(
            @PathVariable Long userId,
            @Valid @RequestBody List<UserExperienceRequestDTO> dto) {

        return ResponseEntity.ok(
                experienceService.addUserExperiences(
                        userId,
                        dto));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<Void> updateExperience(
            @PathVariable Long experienceId,
            @RequestBody UserExperienceRequestDTO dto) {

        experienceService.updateUserExperienceDetails(experienceId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long experienceId) {

        experienceService.removeUserExperience(experienceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserExperienceResponseDTO>> getByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                experienceService.getUserExperienceDetailByUserId(userId));
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<UserExperienceResponseDTO> getById(
            @PathVariable Long experienceId) {

        return ResponseEntity.ok(
                experienceService.getUserExperienceDetailById(experienceId));
    }
}
