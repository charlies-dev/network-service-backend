package com.infy.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.infy.user.enums.EmploymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserExperienceDTO {
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
