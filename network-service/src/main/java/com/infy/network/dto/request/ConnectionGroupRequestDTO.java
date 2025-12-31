package com.infy.network.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionGroupRequestDTO {

    @NotNull(message = "{connection.group.userId.notnull}")
    private Long userId;

    @NotBlank(message = "{connection.group.groupName.notblank}")
    private String name;
}
