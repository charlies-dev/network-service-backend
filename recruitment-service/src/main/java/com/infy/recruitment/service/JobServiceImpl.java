package com.infy.recruitment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.infy.recruitment.dto.request.JobCreateRequestDTO;
import com.infy.recruitment.dto.request.JobSearchRequestDTO;
import com.infy.recruitment.dto.request.JobUpdateRequestDTO;
import com.infy.recruitment.dto.response.JobResponseDTO;
import com.infy.recruitment.entity.Company;
import com.infy.recruitment.entity.Job;
import com.infy.recruitment.exception.InfyLinkedInException;
import com.infy.recruitment.repository.CompanyRepository;
import com.infy.recruitment.repository.JobRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    /* ================= ADD JOB ================= */

    @Override
    @Transactional
    public Long addNewJob(JobCreateRequestDTO dto) {

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() ->
                        new InfyLinkedInException("Company not found"));

        if (dto.getApplicationDeadline().isBefore(LocalDate.now())) {
            throw new InfyLinkedInException("Application deadline must be in future");
        }

        Job job = modelMapper.map(dto, Job.class);
        job.setCompany(company);
        job.setCreatedAt(LocalDateTime.now());

        return jobRepository.save(job).getId();
    }

    /* ================= UPDATE JOB ================= */

    @Override
    @Transactional
    public void updateJob(Long jobId, JobUpdateRequestDTO dto) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Job not found"));

        if (dto.getTitle() != null)
            job.setTitle(dto.getTitle());

        if (dto.getDescription() != null)
            job.setDescription(dto.getDescription());

        if (dto.getSalaryRange() != null)
            job.setSalaryRange(dto.getSalaryRange());

        if (dto.getQualifications() != null)
            job.setQualifications(dto.getQualifications());

        if (dto.getExperienceLevel() != null)
            job.setExperienceLevel(dto.getExperienceLevel());

        if (dto.getLocation() != null)
            job.setLocation(dto.getLocation());

        if (dto.getJobType() != null)
            job.setJobType(dto.getJobType());

        if (dto.getApplicationDeadline() != null)
            job.setApplicationDeadline(dto.getApplicationDeadline());
    }

    /* ================= REMOVE JOB ================= */

    @Override
    @Transactional
    public void removeJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Job not found"));

        jobRepository.delete(job);
    }

    /* ================= GET BY COMPANY ================= */

    @Override
    public List<JobResponseDTO> getJobByCompanyId(Long companyId) {

        return jobRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= GET BY IDS ================= */

    @Override
    public List<JobResponseDTO> getJobByIds(List<Long> jobIds) {

        return jobRepository.findByIdIn(jobIds)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= SEARCH + SORT ================= */

    @Override
    public Page<JobResponseDTO> searchJobAndSort(
            JobSearchRequestDTO dto,
            Pageable pageable) {

        Specification<Job> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (dto.getTitle() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("title")),
                        "%" + dto.getTitle().toLowerCase() + "%"));
            }

            if (dto.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), dto.getLocation()));
            }

            if (dto.getJobType() != null) {
                predicates.add(cb.equal(root.get("jobType"), dto.getJobType()));
            }

            if (dto.getExperienceLevel() != null) {
                predicates.add(cb.equal(root.get("experienceLevel"), dto.getExperienceLevel()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return jobRepository.findAll(spec, pageable)
                .map(this::mapToResponse);
    }

    /* ================= MAPPER ================= */

    private JobResponseDTO mapToResponse(Job job) {
        JobResponseDTO dto = modelMapper.map(job, JobResponseDTO.class);
        dto.setCompanyId(job.getCompany().getId());
        dto.setCompanyName(job.getCompany().getName());
        return dto;
    }
}
