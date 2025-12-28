package com.infy.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyCreateRequestDTO {

    @NotBlank
    private String name;

    private String location;

    private String industry;
}
