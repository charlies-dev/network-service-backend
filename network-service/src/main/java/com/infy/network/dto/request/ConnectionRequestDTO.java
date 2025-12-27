package com.infy.network.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionRequestDTO {

    @NotNull
    private Long userId;              // sender

    @NotNull
    private Long connectedUserId;     // receiver
}

