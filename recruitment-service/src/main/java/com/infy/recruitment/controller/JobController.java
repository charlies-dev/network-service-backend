package com.infy.recruitment.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.recruitment.dto.request.JobCreateRequestDTO;
import com.infy.recruitment.dto.request.JobSearchRequestDTO;
import com.infy.recruitment.dto.response.JobResponseDTO;
import com.infy.recruitment.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponseDTO> getJobById(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                jobService.getJobByJobId(jobId)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<JobResponseDTO>> getActiveJobs() {

        return ResponseEntity.ok(
                jobService.getActiveJobs()
        );
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
    @PostMapping
    public ResponseEntity<Long> createJob(
            @RequestBody JobCreateRequestDTO dto) {

        return ResponseEntity.ok(
                jobService.addNewJob(dto)
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
