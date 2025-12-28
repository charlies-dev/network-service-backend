package com.infy.recruitment.dto.request;

import com.infy.recruitment.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobApplicationStatusUpdateDTO {

    @NotNull
    private ApplicationStatus status;
}
