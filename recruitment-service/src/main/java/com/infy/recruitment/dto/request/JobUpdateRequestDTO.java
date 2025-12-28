package com.infy.recruitment.dto.request;

import java.time.LocalDate;

import com.infy.recruitment.enums.JobType;

import lombok.Data;

@Data
public class JobUpdateRequestDTO {

    private String title;
    private String description;
    private String salaryRange;
    private String qualifications;
    private String experienceLevel;
    private String location;
    private JobType jobType;
    private LocalDate applicationDeadline;
}
