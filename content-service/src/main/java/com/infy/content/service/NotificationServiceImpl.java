package com.infy.content.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infy.content.dto.request.NotificationCreateRequestDTO;
import com.infy.content.dto.response.NotificationResponseDTO;
import com.infy.content.entity.Notification;
import com.infy.content.enums.NotificationType;
import com.infy.content.exception.InfyLinkedInException;
import com.infy.content.repository.NotificationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<NotificationResponseDTO> getNotificationByUserId(
            Long userId,
            Pageable pageable) {

        Page<Notification> notifications =
                notificationRepository.findByUserId(userId, pageable);

        return notifications.map(
                n -> modelMapper.map(n, NotificationResponseDTO.class)
        );
    }

    @Override
    public Page<NotificationResponseDTO> getNotificationByUserIdAndType(
            Long userId,
            NotificationType type,
            Pageable pageable) {

        Page<Notification> notifications =
                notificationRepository.findByUserIdAndType(
                        userId, type, pageable);

        return notifications.map(
                n -> modelMapper.map(n, NotificationResponseDTO.class)
        );
    }

    @Override
    public Page<NotificationResponseDTO> getNotificationByUserIdAndIsRead(
            Long userId,
            Boolean isRead,
            Pageable pageable) {

        Page<Notification> notifications =
                notificationRepository.findByUserIdAndIsRead(
                        userId, isRead, pageable);

        return notifications.map(
                n -> modelMapper.map(n, NotificationResponseDTO.class)
        );
    }

    @Override
    @Transactional
    public NotificationResponseDTO getNotificationByNotificationId(
            Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new InfyLinkedInException(
                                        "Notification not found"));

        if (!notification.getIsRead()) {
            notification.setIsRead(true);
        }

        return modelMapper.map(
                notification,
                NotificationResponseDTO.class
        );
    }

    @Override
    @Transactional
    public Long addNotification(NotificationCreateRequestDTO dto) {

        Notification notification = Notification.builder()
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .type(dto.getType())
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification).getId();
    }

    @Override
    public Long getUnreadNotificationCount(Long userId) {

        if (userId == null) {
            throw new InfyLinkedInException("User id is required");
        }

        return notificationRepository.countByUserIdAndIsRead(userId, false);
    }
}
