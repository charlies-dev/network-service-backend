package com.infy.recruitment.dto.request;

import com.infy.recruitment.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationCreateRequestDTO {

    private Long userId;

    private String message;

    private NotificationType type;
}
