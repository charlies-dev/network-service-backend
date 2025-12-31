package com.infy.recruitment.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobApplicationRequestDTO {
    @NotNull(message = "{job.application.jobId.notnull}")
    private Long jobId;

    @NotNull(message = "{job.application.userId.notnull}")
    private Long userId;

    @NotNull
    private MultipartFile resume;          

    private MultipartFile coverLetter;     
}
