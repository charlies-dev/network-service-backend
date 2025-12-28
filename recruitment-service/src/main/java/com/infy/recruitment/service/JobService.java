package com.infy.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.infy.recruitment.dto.request.JobCreateRequestDTO;
import com.infy.recruitment.dto.request.JobSearchRequestDTO;
import com.infy.recruitment.dto.request.JobUpdateRequestDTO;
import com.infy.recruitment.dto.response.JobResponseDTO;

public interface JobService {

     Long addNewJob(JobCreateRequestDTO requestDTO);

    void updateJob(Long jobId, JobUpdateRequestDTO requestDTO);

    void removeJob(Long jobId);

    List<JobResponseDTO> getJobByCompanyId(Long companyId);

    List<JobResponseDTO> getJobByIds(List<Long> jobIds);

    Page<JobResponseDTO> searchJobAndSort(
            JobSearchRequestDTO requestDTO,
            Pageable pageable
    );
}
