package com.infy.recruitment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SavedJobRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long jobId;
}
