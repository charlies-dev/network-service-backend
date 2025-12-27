package com.infy.network.dto.request;

import com.infy.network.enums.ConnectionStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionStatusUpdateDTO {

    @NotNull
    private ConnectionStatus status;
}
