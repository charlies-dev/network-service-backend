package com.infy.user.dto.response;

import java.time.LocalDate;

import com.infy.user.enums.EmploymentType;

import lombok.Data;

@Data
public class UserExperienceResponseDTO {

    private Long id;
    private String companyName;
    private String jobTitle;
    private EmploymentType employmentType;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String description;
}
