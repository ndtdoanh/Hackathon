package com.hacof.communication.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.entity.Notification;
import com.hacof.communication.entity.User;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.mapper.NotificationMapper;
import com.hacof.communication.repository.NotificationRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.NotificationService;
import com.hacof.communication.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    UserRepository userRepository;
    SecurityUtil securityUtil;

    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        User recipient = userRepository
                .findById(request.getRecipientId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        User sender = securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .notificationType(request.getNotificationType())
                .content(request.getContent())
                .metadata(request.getMetadata())
                .isRead(false)
                .build();

        notificationRepository.save(notification);
        return notificationMapper.toNotificationResponse(notification);
    }

    @Override
    public List<NotificationResponse> getNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();
    }

    @Override
    public NotificationResponse getNotification(Long id) {
        Notification notification = notificationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));
        return notificationMapper.toNotificationResponse(notification);
    }

    @Override
    public NotificationResponse updateNotification(Long id, NotificationRequest request) {
        Notification notification = notificationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notificationMapper.updateNotificationFromRequest(request, notification);
        notification = notificationRepository.save(notification);

        return notificationMapper.toNotificationResponse(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }
        notificationRepository.deleteById(id);
    }
}
