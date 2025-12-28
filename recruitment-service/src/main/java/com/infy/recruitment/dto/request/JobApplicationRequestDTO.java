package com.infy.recruitment.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class JobApplicationRequestDTO {
    @NotNull
    private Long jobId;

    @NotNull
    private Long userId;

    /* Files */
    @NotNull
    private MultipartFile resume;          // required for apply

    private MultipartFile coverLetter;     // optional
}
