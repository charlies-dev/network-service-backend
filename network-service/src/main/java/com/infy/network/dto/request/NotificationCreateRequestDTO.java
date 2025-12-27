package com.infy.network.dto.request;

import com.infy.network.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationCreateRequestDTO {

    private Long userId;

    private String message;

    private NotificationType type;
}
