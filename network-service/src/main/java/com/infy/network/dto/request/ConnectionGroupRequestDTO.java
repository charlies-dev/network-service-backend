package com.infy.network.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionGroupRequestDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;
}
