package com.infy.user.dto.response;

import lombok.Data;

@Data
public class EducationResponseDTO {
    private Long id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
}
