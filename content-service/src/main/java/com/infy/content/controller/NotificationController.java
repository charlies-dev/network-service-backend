package com.infy.content.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.content.dto.request.NotificationCreateRequestDTO;
import com.infy.content.dto.response.NotificationResponseDTO;
import com.infy.content.enums.NotificationType;
import com.infy.content.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<NotificationResponseDTO>> getByUser(
            @PathVariable Long userId,
            Pageable pageable) {

        return ResponseEntity.ok(
                notificationService.getNotificationByUserId(userId, pageable)
        );
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<Page<NotificationResponseDTO>> getByUserAndType(
            @PathVariable Long userId,
            @PathVariable NotificationType type,
            Pageable pageable) {

        return ResponseEntity.ok(
                notificationService.getNotificationByUserIdAndType(
                        userId, type, pageable)
        );
    }

    @GetMapping("/user/{userId}/read/{isRead}")
    public ResponseEntity<Page<NotificationResponseDTO>> getByUserAndRead(
            @PathVariable Long userId,
            @PathVariable Boolean isRead,
            Pageable pageable) {

        return ResponseEntity.ok(
                notificationService.getNotificationByUserIdAndIsRead(
                        userId, isRead, pageable)
        );
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> getById(
            @PathVariable Long notificationId) {

        return ResponseEntity.ok(
                notificationService
                        .getNotificationByNotificationId(notificationId)
        );
    }

    @PostMapping
    public ResponseEntity<Long> addNotification(
            @Valid @RequestBody NotificationCreateRequestDTO dto) {

        return ResponseEntity.ok(
                notificationService.addNotification(dto)
        );
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadNotificationCount(userId)
        );
    }
}
