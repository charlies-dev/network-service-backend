package com.infy.content.dto.response;

import java.time.LocalDateTime;

import com.infy.content.enums.NotificationType;

import lombok.Data;

@Data
public class NotificationResponseDTO {

    private Long id;
    private Long userId;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
