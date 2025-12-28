package com.infy.recruitment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infy.recruitment.clients.UserClient;
import com.infy.recruitment.dto.request.JobApplicationRequestDTO;
import com.infy.recruitment.dto.request.JobApplicationStatusUpdateDTO;
import com.infy.recruitment.dto.response.JobApplicationResponseDTO;
import com.infy.recruitment.dto.response.UserDetailsDTO;
import com.infy.recruitment.entity.Job;
import com.infy.recruitment.entity.JobApplication;
import com.infy.recruitment.enums.ApplicationStatus;
import com.infy.recruitment.exception.InfyLinkedInException;
import com.infy.recruitment.repository.JobApplicationRepository;
import com.infy.recruitment.repository.JobRepository;
import com.infy.recruitment.util.FileUploadUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserClient userClient;
    private final FileUploadUtil fileUploadUtil;
    private final ModelMapper modelMapper;

    @Override
    public Long applyForJob(JobApplicationRequestDTO dto) {
        userClient.validateUserExists(dto.getUserId());

        Job job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new InfyLinkedInException("Job not found"));

        if (applicationRepository.existsByJobIdAndUserId(
                dto.getJobId(), dto.getUserId())) {
            throw new InfyLinkedInException("Already applied for this job");
        }

        if (dto.getResume() == null || dto.getResume().isEmpty()) {
            throw new InfyLinkedInException("Resume is required");
        }

        String resumeUrl = fileUploadUtil.uploadPdf(dto.getResume());
        String coverLetterUrl = fileUploadUtil.uploadPdf(dto.getCoverLetter());

        JobApplication application = JobApplication.builder()
                .job(job)
                .userId(dto.getUserId())
                .resumeUrl(resumeUrl)
                .coverLetterUrl(coverLetterUrl)
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .build();

        return applicationRepository.save(application).getId();
    }

    @Override
    public void updateApplication(Long applicationId, MultipartFile resume, MultipartFile coverLetter) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new InfyLinkedInException(
                        "Application not found"));

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new InfyLinkedInException("Withdrawn application cannot be updated");
        }

        if (resume != null) {
            application.setResumeUrl(
                    fileUploadUtil.uploadPdf(resume));
        }

        if (coverLetter != null) {
            application.setCoverLetterUrl(
                    fileUploadUtil.uploadPdf(coverLetter));
        }
    }

    @Override
    @Transactional
    public void updateStatus(
            Long applicationId,
            JobApplicationStatusUpdateDTO dto) {

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new InfyLinkedInException(
                        "Application not found"));

        application.setStatus(dto.getStatus());
    }

    /* ================= GET BY USER ================= */

    @Override
    public List<JobApplicationResponseDTO> getApplicationByUserId(Long userId) {

        userClient.validateUserExists(userId);

        return applicationRepository
                .findByUserIdOrderByAppliedAtDesc(userId)
                .stream()
                .map(app -> {
                    JobApplicationResponseDTO dto = modelMapper.map(app, JobApplicationResponseDTO.class);
                    dto.setJobId(app.getJob().getId());
                    dto.setJobTitle(app.getJob().getTitle());
                    dto.setCompanyName(app.getJob().getCompany().getName());
                      UserDetailsDTO user = userClient.getUserDetailsById(app.getUserId());
                    dto.setUserDetails(user);;
                    return dto;
                })
                .toList();
    }

    /* ================= GET BY JOB ================= */

    @Override
    public List<JobApplicationResponseDTO> getApplicationByJobId(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new InfyLinkedInException("Job not found"));

        return applicationRepository
                .findByJobIdOrderByAppliedAtDesc(jobId)
                .stream()
                .map(app -> {
                    JobApplicationResponseDTO dto = modelMapper.map(app, JobApplicationResponseDTO.class);
                    dto.setJobId(job.getId());
                    dto.setJobTitle(job.getTitle());

                    UserDetailsDTO user = userClient.getUserDetailsById(app.getUserId());
                    dto.setUserDetails(user);;
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional
    public void withdrawApplication(Long applicationId, Long userId) {

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new InfyLinkedInException(
                        "Application not found"));

        if (!application.getUserId().equals(userId)) {
            throw new InfyLinkedInException(
                    "User not authorized to withdraw this application");
        }

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new InfyLinkedInException(
                    "Application already withdrawn");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
    }

}
