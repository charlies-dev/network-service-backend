package com.infy.user.dto.request;

import java.time.LocalDate;

import com.infy.user.enums.EmploymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserExperienceRequestDTO {

    @NotBlank
    private String companyName;

    @NotBlank
    private String jobTitle;

    @NotNull
    private EmploymentType employmentType;

    private String location;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Boolean isCurrent;

    private String description;
}
