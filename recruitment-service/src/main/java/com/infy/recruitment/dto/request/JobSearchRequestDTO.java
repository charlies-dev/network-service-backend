package com.infy.recruitment.dto.request;

import com.infy.recruitment.enums.JobType;

import lombok.Data;

@Data
public class JobSearchRequestDTO {

    private String title;
    private String location;
    private JobType jobType;
    private String experienceLevel;
    private String salaryRange;
}
