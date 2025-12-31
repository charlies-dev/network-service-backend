package com.infy.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionStatusDTO {
    
    private Long userId;
    private Long targetUserId;
    private Boolean connected;
    private Boolean requestPending;
    private String connectionMessage;
}
