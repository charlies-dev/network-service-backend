package com.infy.recruitment.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.infy.recruitment.enums.JobType;

import lombok.Data;

@Data
public class SavedJobResponseDTO {

    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private JobType jobType;
    private LocalDate applicationDeadline;
    private LocalDateTime savedAt;
}

