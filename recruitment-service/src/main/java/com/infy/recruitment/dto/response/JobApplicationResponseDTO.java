package com.infy.recruitment.dto.response;

import java.time.LocalDateTime;

import com.infy.recruitment.enums.ApplicationStatus;

import lombok.Data;

@Data
public class JobApplicationResponseDTO {

    private Long id;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    /* Job Details */
    private Long jobId;
    private String jobTitle;
    private String companyName;

    /* User Details */
    UserDetailsDTO userDetails;

    private String resumeUrl;
    private String coverLetterUrl;
}
