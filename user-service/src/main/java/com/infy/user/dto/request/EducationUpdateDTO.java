package com.infy.user.dto.request;

import lombok.Data;

@Data
public class EducationUpdateDTO {

    private String institution;

    private String degree;

    private String fieldOfStudy;

    private Integer startYear;

    private Integer endYear;
}