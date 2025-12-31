package com.infy.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionCountDTO {
    private Long userId;
    private Long connectionCount;
    private Long pendingRequestCount;
}
