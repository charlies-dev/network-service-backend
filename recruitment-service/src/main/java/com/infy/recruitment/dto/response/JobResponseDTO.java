package com.infy.recruitment.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.infy.recruitment.enums.JobType;

import lombok.Data;

@Data
public class JobResponseDTO {

    private Long id;
    private Long companyId;
    private String companyName;
    private String title;
    private String description;
    private String salaryRange;
    private String qualifications;
    private String experienceLevel;
    private String location;
    private JobType jobType;
    private LocalDate applicationDeadline;
    private LocalDateTime createdAt;
}

