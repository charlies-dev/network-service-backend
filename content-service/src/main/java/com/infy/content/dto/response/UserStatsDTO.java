package com.infy.content.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
    private Long userId;
    
    private Long connectionCount;
    private Long pendingRequestCount;
    
    private Long postCount;
    private Long publishedPostCount;
    private Long draftPostCount;
}
