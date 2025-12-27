package com.infy.user.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationDTO {
    
    @NotBlank
    private String institution;

    private String degree;

    private String fieldOfStudy;

    @NotNull
    private Integer startYear;

    private Integer endYear;
}
