package com.infy.network.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConnectionGroupResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private LocalDateTime addedAt;
}
