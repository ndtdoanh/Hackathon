package com.hacof.communication.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.dto.request.NotificationDeliveryRequest;
import com.hacof.communication.dto.response.NotificationDeliveryResponse;
import com.hacof.communication.entity.Notification;
import com.hacof.communication.entity.NotificationDelivery;
import com.hacof.communication.entity.Role;
import com.hacof.communication.entity.User;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.mapper.NotificationDeliveryMapper;
import com.hacof.communication.repository.NotificationDeliveryRepository;
import com.hacof.communication.repository.NotificationRepository;
import com.hacof.communication.repository.RoleRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.repository.UserRoleRepository;
import com.hacof.communication.service.NotificationDeliveryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationDeliveryServiceImpl implements NotificationDeliveryService {
    NotificationDeliveryRepository notificationDeliveryRepository;
    NotificationRepository notificationRepository;
    NotificationDeliveryMapper notificationDeliveryMapper;
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    RoleRepository roleRepository;

    @Override
    public List<NotificationDeliveryResponse> createNotificationDelivery(
            Long notificationId, NotificationDeliveryRequest request) {

        if ((request.getRecipientIds() != null && !request.getRecipientIds().isEmpty()) && request.getRole() != null) {
            throw new AppException(ErrorCode.INVALID_NOTIFICATION_REQUEST);
        }

        if ((request.getRecipientIds() == null || request.getRecipientIds().isEmpty()) && request.getRole() == null) {
            throw new AppException(ErrorCode.INVALID_NOTIFICATION_REQUEST);
        }

        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        Set<User> recipients = new HashSet<>();

        if (request.getRecipientIds() != null && !request.getRecipientIds().isEmpty()) {
            recipients.addAll(userRepository.findAllById(request.getRecipientIds()));

            if (recipients.size() != request.getRecipientIds().size()) {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
        } else if (request.getRole() != null) {
            Role role = roleRepository
                    .findByName(request.getRole().name())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));

            List<User> usersWithRole = userRoleRepository.findUsersByRole(role);

            if (usersWithRole.isEmpty()) {
                throw new AppException(ErrorCode.NO_USERS_WITH_ROLE);
            }

            recipients.addAll(usersWithRole);
        }

        List<NotificationDelivery> deliveries = recipients.stream()
                .map(user -> NotificationDelivery.builder()
                        .notification(notification)
                        .recipient(user)
                        .role(request.getRole())
                        .method(request.getMethod())
                        .status(NotificationStatus.PENDING)
                        .build())
                .collect(Collectors.toList());

        notificationDeliveryRepository.saveAll(deliveries);

        return deliveries.stream()
                .map(notificationDeliveryMapper::toNotificationDeliveryResponse)
                .toList();
    }

    @Override
    public NotificationDeliveryResponse updateNotificationDeliveryStatus(Long deliveryId, NotificationStatus status) {
        NotificationDelivery delivery = notificationDeliveryRepository
                .findById(deliveryId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_DELIVERY_NOT_FOUND));

        delivery.setStatus(status);
        notificationDeliveryRepository.save(delivery);

        return notificationDeliveryMapper.toNotificationDeliveryResponse(delivery);
    }

    @Override
    public List<NotificationDeliveryResponse> getDeliveriesByNotification(Long notificationId) {
        List<NotificationDelivery> deliveries = notificationDeliveryRepository.findByNotificationId(notificationId);
        return deliveries.stream()
                .map(notificationDeliveryMapper::toNotificationDeliveryResponse)
                .toList();
    }
}
