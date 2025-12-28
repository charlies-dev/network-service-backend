package com.infy.recruitment.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.recruitment.dto.request.JobCreateRequestDTO;
import com.infy.recruitment.dto.request.JobSearchRequestDTO;
import com.infy.recruitment.dto.request.JobUpdateRequestDTO;
import com.infy.recruitment.dto.response.JobResponseDTO;
import com.infy.recruitment.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<Long> addJob(
            @Valid @RequestBody JobCreateRequestDTO dto) {

        return ResponseEntity.ok(
                jobService.addNewJob(dto)
        );
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<Void> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobUpdateRequestDTO dto) {

        jobService.updateJob(jobId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long jobId) {

        jobService.removeJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponseDTO>> getByCompany(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                jobService.getJobByCompanyId(companyId)
        );
    }

    @PostMapping("/ids")
    public ResponseEntity<List<JobResponseDTO>> getByIds(
            @RequestBody List<Long> ids) {

        return ResponseEntity.ok(
                jobService.getJobByIds(ids)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<Page<JobResponseDTO>> search(
            @RequestBody JobSearchRequestDTO dto,
            Pageable pageable) {

        return ResponseEntity.ok(
                jobService.searchJobAndSort(dto, pageable)
        );
    }
}
