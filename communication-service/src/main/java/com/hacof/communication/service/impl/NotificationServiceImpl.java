package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.constant.RoleType;
import com.hacof.communication.dto.request.NotificationDeliveryRequest;
import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.request.UpdateNotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.entity.Notification;
import com.hacof.communication.entity.NotificationDelivery;
import com.hacof.communication.entity.Role;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.UserRole;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.mapper.NotificationMapper;
import com.hacof.communication.repository.NotificationDeliveryRepository;
import com.hacof.communication.repository.NotificationRepository;
import com.hacof.communication.repository.RoleRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.repository.UserRoleRepository;
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
    RoleRepository roleRepository;
    UserRoleRepository userRoleRepository;
    NotificationDeliveryRepository notificationDeliveryRepository;

    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        User sender = securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = notificationMapper.toNotification(request, sender);
        notificationRepository.save(notification);

        NotificationDeliveryRequest deliveryRequest = request.getNotificationDeliveryRequest();

        if (deliveryRequest != null) {
            if ((deliveryRequest.getRecipientIds() != null
                            && !deliveryRequest.getRecipientIds().isEmpty())
                    && deliveryRequest.getRole() != null) {
                throw new AppException(ErrorCode.INVALID_NOTIFICATION_REQUEST);
            }

            if ((deliveryRequest.getRecipientIds() == null
                            || deliveryRequest.getRecipientIds().isEmpty())
                    && deliveryRequest.getRole() == null) {
                throw new AppException(ErrorCode.INVALID_NOTIFICATION_REQUEST);
            }

            Set<User> recipients = new HashSet<>();
            Map<Long, Role> userRoleMap = new HashMap<>();

            if (deliveryRequest.getRecipientIds() != null
                    && !deliveryRequest.getRecipientIds().isEmpty()) {
                recipients.addAll(userRepository.findAllById(deliveryRequest.getRecipientIds()));

                if (recipients.size() != deliveryRequest.getRecipientIds().size()) {
                    throw new AppException(ErrorCode.USER_NOT_EXISTED);
                }

                List<UserRole> userRoles = userRoleRepository.findUserRolesByUsers(new ArrayList<>(recipients));
                userRoles.forEach(ur -> userRoleMap.put(ur.getUser().getId(), ur.getRole()));
            } else if (deliveryRequest.getRole() != null) {
                Role role = roleRepository
                        .findByName(deliveryRequest.getRole().name())
                        .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));

                List<User> usersWithRole = userRoleRepository.findUsersByRole(role);

                if (usersWithRole.isEmpty()) {
                    throw new AppException(ErrorCode.NO_USERS_WITH_ROLE);
                }

                recipients.addAll(usersWithRole);
                usersWithRole.forEach(user -> userRoleMap.put(user.getId(), role));
            }

            List<NotificationDelivery> deliveries = recipients.stream()
                    .map(user -> NotificationDelivery.builder()
                            .notification(notification)
                            .recipient(user)
                            .role(RoleType.fromString(
                                    userRoleMap.get(user.getId()).getName()))
                            .method(deliveryRequest.getMethod())
                            .status(NotificationStatus.PENDING)
                            .build())
                    .collect(Collectors.toList());

            notificationDeliveryRepository.saveAll(deliveries);
            notification.setNotificationDeliveries(deliveries);
        }

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
    public List<NotificationResponse> getNotificationsBySenderId(Long senderId) {
        List<Notification> notifications = notificationRepository.findBySenderId(senderId);
        return notifications.stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();
    }

    @Override
    public NotificationResponse updateNotification(Long id, UpdateNotificationRequest request) {
        Notification notification = notificationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if (request.getContent() != null) {
            notification.setContent(request.getContent());
        }
        if (request.getMetadata() != null) {
            notification.setMetadata(request.getMetadata());
        }

        notification = notificationRepository.save(notification);

        List<NotificationDelivery> deliveries = notificationDeliveryRepository.findByNotification(notification);
        if (!deliveries.isEmpty()) {
            deliveries.forEach(delivery -> {
                if (request.getMethod() != null) {
                    delivery.setMethod(request.getMethod());
                }
                if (request.getStatus() != null) {
                    delivery.setStatus(request.getStatus());
                }
            });
            notificationDeliveryRepository.saveAll(deliveries);
        }

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
