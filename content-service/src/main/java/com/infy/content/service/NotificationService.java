package com.infy.content.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.infy.content.dto.request.NotificationCreateRequestDTO;
import com.infy.content.dto.response.NotificationResponseDTO;
import com.infy.content.enums.NotificationType;

public interface NotificationService {

   Page<NotificationResponseDTO> getNotificationByUserId(
            Long userId,
            Pageable pageable
    );

    Page<NotificationResponseDTO> getNotificationByUserIdAndType(
            Long userId,
            NotificationType type,
            Pageable pageable
    );

    Page<NotificationResponseDTO> getNotificationByUserIdAndIsRead(
            Long userId,
            Boolean isRead,
            Pageable pageable
    );
Long addNotification(NotificationCreateRequestDTO requestDTO);

    NotificationResponseDTO getNotificationByNotificationId(Long notificationId);
     Long getUnreadNotificationCount(Long userId);
}
