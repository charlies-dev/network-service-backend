package com.infy.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationRequestDTO {

    @NotBlank
    private String institution;

    private String degree;

    private String fieldOfStudy;

    @NotNull
    private Integer startYear;

    private Integer endYear;
}

