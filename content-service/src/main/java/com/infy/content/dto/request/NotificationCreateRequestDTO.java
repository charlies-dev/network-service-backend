package com.infy.content.dto.request;

import com.infy.content.enums.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationCreateRequestDTO {
    @NotNull
    private Long userId;

    @NotBlank
    private String message;

    @NotNull
    private NotificationType type;
}
