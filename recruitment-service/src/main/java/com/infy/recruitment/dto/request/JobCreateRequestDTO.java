package com.infy.recruitment.dto.request;

import java.time.LocalDate;

import com.infy.recruitment.enums.JobType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobCreateRequestDTO {

    @NotNull
    private Long companyId;

    @NotBlank
    private String title;

    private String description;

    private String salaryRange;

    private String qualifications;

    private String experienceLevel;

    private String location;

    @NotNull
    private JobType jobType;

    @NotNull
    @Future
    private LocalDate applicationDeadline;
}

