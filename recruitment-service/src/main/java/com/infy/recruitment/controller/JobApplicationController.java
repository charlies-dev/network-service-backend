package com.infy.recruitment.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infy.recruitment.dto.request.JobApplicationRequestDTO;
import com.infy.recruitment.dto.request.JobApplicationStatusUpdateDTO;
import com.infy.recruitment.dto.response.JobApplicationResponseDTO;
import com.infy.recruitment.service.JobApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> apply(
            @Valid @ModelAttribute  JobApplicationRequestDTO dto) {

        return ResponseEntity.ok(
                jobApplicationService.applyForJob(dto)
        );
    }

    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody JobApplicationStatusUpdateDTO dto) {

        jobApplicationService.updateStatus(applicationId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JobApplicationResponseDTO>> getByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                jobApplicationService.getApplicationByUserId(userId)
        );
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplicationResponseDTO>> getByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                jobApplicationService.getApplicationByJobId(jobId)
        );
    }

    @PutMapping(
            value = "/{applicationId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> update(
            @PathVariable Long applicationId,
            @RequestPart(value = "resume", required = false)
                    MultipartFile resume,
            @RequestPart(value = "coverLetter", required = false)
                    MultipartFile coverLetter) {

        jobApplicationService.updateApplication(
                applicationId, resume, coverLetter);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{applicationId}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {

        jobApplicationService.withdrawApplication(
                applicationId, userId);
        return ResponseEntity.noContent().build();
    }
}
