package com.infy.recruitment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.infy.recruitment.clients.UserClient;
import com.infy.recruitment.dto.request.SavedJobRequestDTO;
import com.infy.recruitment.dto.response.SavedJobResponseDTO;
import com.infy.recruitment.entity.Job;
import com.infy.recruitment.entity.SavedJob;
import com.infy.recruitment.entity.SavedJobId;
import com.infy.recruitment.exception.InfyLinkedInException;
import com.infy.recruitment.repository.JobRepository;
import com.infy.recruitment.repository.SavedJobRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {
    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserClient userClient;

    /* ================= SAVE JOB ================= */

    @Override
    @Transactional
    public void saveJob(SavedJobRequestDTO dto) {

        userClient.validateUserExists(dto.getUserId());

     jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new InfyLinkedInException("Job not found"));

        if (savedJobRepository.existsByUserIdAndJobId(
                dto.getUserId(), dto.getJobId())) {
            throw new InfyLinkedInException("Job already saved");
        }

        SavedJob savedJob = SavedJob.builder()
                .userId(dto.getUserId())
                .jobId(dto.getJobId())
                .savedAt(LocalDateTime.now())
                .build();

        savedJobRepository.save(savedJob);
    }

    /* ================= REMOVE SAVED JOB ================= */

    @Override
    @Transactional
    public void removeSavedJob(Long userId, Long jobId) {

        SavedJobId id = new SavedJobId(userId, jobId);

        SavedJob savedJob = savedJobRepository.findById(id)
                .orElseThrow(() -> new InfyLinkedInException("Saved job not found"));

        savedJobRepository.delete(savedJob);
    }

    /* ================= GET SAVED JOBS ================= */

    @Override
    public List<SavedJobResponseDTO> getSavedJobByUserId(Long userId) {

        userClient.validateUserExists(userId);

        return savedJobRepository
                .findByUserIdOrderBySavedAtDesc(userId)
                .stream()
                .map(savedJob -> {
                    Job job = savedJob.getJob();

                    SavedJobResponseDTO dto = new SavedJobResponseDTO();
                    dto.setJobId(job.getId());
                    dto.setJobTitle(job.getTitle());
                    dto.setCompanyName(job.getCompany().getName());
                    dto.setLocation(job.getLocation());
                    dto.setJobType(job.getJobType());
                    dto.setApplicationDeadline(job.getApplicationDeadline());
                    dto.setSavedAt(savedJob.getSavedAt());
                    return dto;
                })
                .toList();
    }

}
