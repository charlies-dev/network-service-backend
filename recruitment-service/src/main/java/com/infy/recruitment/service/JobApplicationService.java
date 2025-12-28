package com.infy.recruitment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.infy.recruitment.dto.request.JobApplicationRequestDTO;
import com.infy.recruitment.dto.request.JobApplicationStatusUpdateDTO;
import com.infy.recruitment.dto.response.JobApplicationResponseDTO;

public interface JobApplicationService {

   Long applyForJob(
            JobApplicationRequestDTO requestDTO
    );

    void updateApplication(
            Long applicationId,
            MultipartFile resume,
            MultipartFile coverLetter
    );

    void updateStatus(
            Long applicationId,
            JobApplicationStatusUpdateDTO requestDTO
    );

     void withdrawApplication(Long applicationId, Long userId);

    List<JobApplicationResponseDTO> getApplicationByUserId(Long userId);

    List<JobApplicationResponseDTO> getApplicationByJobId(Long jobId);
}
