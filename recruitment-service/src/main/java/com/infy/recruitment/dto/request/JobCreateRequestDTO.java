package com.infy.recruitment.dto.request;

import java.time.LocalDate;

import com.infy.recruitment.enums.JobType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobCreateRequestDTO {

    @NotNull(message = "{job.companyId.notnull}")
    private Long companyId;

    @NotBlank(message = "{job.title.notblank}")
    private String title;

    private String description;

    private String salaryRange;

    private String qualifications;

    private String experienceLevel;

    private String location;

    @NotNull(message = "{job.jobType.notnull}")
    private JobType jobType;

    @NotNull(message = "{job.applicationDeadline.notnull}")
    @Future(message = "{job.applicationDeadline.future}")
    private LocalDate applicationDeadline;
}
