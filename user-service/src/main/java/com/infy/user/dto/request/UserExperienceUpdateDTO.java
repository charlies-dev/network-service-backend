package com.infy.user.dto.request;

import java.time.LocalDate;

import com.infy.user.enums.EmploymentType;

import lombok.Data;

@Data
public class UserExperienceUpdateDTO {

    private String companyName;
    private String jobTitle;
    private EmploymentType employmentType;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String description;
}
