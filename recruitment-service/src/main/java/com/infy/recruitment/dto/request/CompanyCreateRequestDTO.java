package com.infy.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyCreateRequestDTO {

    @NotBlank(message = "{company.name.notblank}")
    private String name;

    private String location;

    private String industry;
}
