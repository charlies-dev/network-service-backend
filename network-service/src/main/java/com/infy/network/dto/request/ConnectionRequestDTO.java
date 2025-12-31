package com.infy.network.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionRequestDTO {

    @NotNull(message = "{connection.userId.notnull}")
    private Long userId;              

    @NotNull(message = "{connection.connectedUserId.notnull}")
    private Long connectedUserId;     
}
