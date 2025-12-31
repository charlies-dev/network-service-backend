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

import com.infy.user.dto.request.EducationRequestDTO;
import com.infy.user.dto.response.EducationResponseDTO;
import com.infy.user.service.EducationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Long> addEducation(
            @PathVariable Long userId,
            @Valid @RequestBody EducationRequestDTO requestDTO) {

        return ResponseEntity.ok(
                educationService.addUserEducation(userId, requestDTO));
    }

    @PostMapping("/user/{userId}/bulk")
    public ResponseEntity<List<Long>> addEducations(
            @PathVariable Long userId,
            @Valid @RequestBody List<EducationRequestDTO> requestDTO) {

        return ResponseEntity.ok(
                educationService.addUserEducations(
                        userId,
                        requestDTO));
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<Void> updateEducation(
            @PathVariable Long educationId,
            @RequestBody EducationRequestDTO requestDTO) {

        educationService.updateEducationDetails(educationId, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable Long educationId) {

        educationService.removeEducation(educationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EducationResponseDTO>> getByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                educationService.getEducationDetailByUserId(userId));
    }

    @GetMapping("/{educationId}")
    public ResponseEntity<EducationResponseDTO> getById(
            @PathVariable Long educationId) {

        return ResponseEntity.ok(
                educationService.getEducationDetailById(educationId));
    }
}
