package com.infy.content.repository;

import com.infy.content.entity.Notification;
import com.infy.content.enums.NotificationType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserId(
            Long userId,
            Pageable pageable
    );

    Page<Notification> findByUserIdAndType(
            Long userId,
            NotificationType type,
            Pageable pageable
    );

    Page<Notification> findByUserIdAndIsRead(
            Long userId,
            Boolean isRead,
            Pageable pageable
    );

     long countByUserIdAndIsRead(Long userId, Boolean isRead);
}
