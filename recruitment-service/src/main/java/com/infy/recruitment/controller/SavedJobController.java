package com.infy.recruitment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.recruitment.dto.request.SavedJobRequestDTO;
import com.infy.recruitment.dto.response.SavedJobResponseDTO;
import com.infy.recruitment.service.SavedJobService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/saved-jobs")
@RequiredArgsConstructor
public class SavedJobController {

    private final SavedJobService savedJobService;

    @PostMapping
    public ResponseEntity<Void> saveJob(
            @Valid @RequestBody SavedJobRequestDTO requestDTO) {

        savedJobService.saveJob(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/{jobId}")
    public ResponseEntity<Void> removeSavedJob(
            @PathVariable Long userId,
            @PathVariable Long jobId) {

        savedJobService.removeSavedJob(userId, jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    @PermitAll
    public ResponseEntity<List<SavedJobResponseDTO>> getSavedJobsByUser(
            @PathVariable Long userId) {

        List<SavedJobResponseDTO> savedJobs = savedJobService.getSavedJobByUserId(userId);
        return ResponseEntity.ok(savedJobs);
    }
}
