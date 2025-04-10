package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.communication.constant.NotificationMethod;
import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.constant.RoleType;
import com.hacof.communication.dto.request.BulkUpdateReadStatusRequest;
import com.hacof.communication.dto.request.NotificationDeliveryRequest;
import com.hacof.communication.dto.request.NotificationRequest;
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
import com.hacof.communication.service.EmailService;
import com.hacof.communication.service.NotificationService;
import com.hacof.communication.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    UserRepository userRepository;
    SecurityUtil securityUtil;
    RoleRepository roleRepository;
    UserRoleRepository userRoleRepository;
    NotificationDeliveryRepository notificationDeliveryRepository;
    EmailService emailService;

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
                    .filter(user -> {
                        if (deliveryRequest.getMethod() == NotificationMethod.EMAIL) {
                            return user.getEmail() != null && !user.getEmail().isEmpty();
                        }
                        return true;
                    })
                    .map(user -> NotificationDelivery.builder()
                            .notification(notification)
                            .recipient(user)
                            .role(RoleType.fromString(
                                    userRoleMap.get(user.getId()).getName()))
                            .method(deliveryRequest.getMethod())
                            .status(NotificationStatus.SENT)
                            .build())
                    .collect(Collectors.toList());

            if (!deliveries.isEmpty()) {
                notificationDeliveryRepository.saveAll(deliveries);
                notification.setNotificationDeliveries(deliveries);

                if (deliveryRequest.getMethod() == NotificationMethod.EMAIL) {
                    sendEmailToUsersAsync(deliveries);
                }
            }
        }

        return notificationMapper.toNotificationResponse(notification);
    }

    public void sendEmailToUsersAsync(List<NotificationDelivery> deliveries) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (NotificationDelivery delivery : deliveries) {
            User recipient = delivery.getRecipient();
            Notification notification = delivery.getNotification();

            if (recipient.getEmail() != null && !recipient.getEmail().isEmpty()) {
                String subject = "New announcement from the system";
                String content = buildNotificationEmailContent(notification.getContent(), notification.getMetadata());

                CompletableFuture<Void> future = emailService
                        .sendEmailAsync(recipient.getEmail(), subject, content)
                        .thenRun(() -> {
                            delivery.setStatus(NotificationStatus.SENT);
                            notificationDeliveryRepository.save(delivery);
                        })
                        .exceptionally(ex -> {
                            delivery.setStatus(NotificationStatus.FAILED);
                            notificationDeliveryRepository.save(delivery);
                            log.error("Failed to send email to {}", recipient.getEmail(), ex);
                            return null;
                        });

                futures.add(future);
            }
        }
    }

    public void sendEmailToUsers(List<NotificationDelivery> deliveries) {
        for (NotificationDelivery delivery : deliveries) {
            User recipient = delivery.getRecipient();
            Notification notification = delivery.getNotification();

            if (recipient.getEmail() != null && !recipient.getEmail().isEmpty()) {
                try {
                    String subject = "New announcement from the system";
                    String content =
                            buildNotificationEmailContent(notification.getContent(), notification.getMetadata());
                    emailService.sendEmail(recipient.getEmail(), subject, content);

                    delivery.setStatus(NotificationStatus.SENT);
                    notificationDeliveryRepository.save(delivery);
                } catch (MessagingException e) {
                    delivery.setStatus(NotificationStatus.FAILED);
                    notificationDeliveryRepository.save(delivery);
                    e.printStackTrace();
                }
            }
        }
    }

    private String buildNotificationEmailContent(String content, String metadata) {
        return "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>"
                + "<h2 style='color: #333;'>System notice</h2>"
                + "<p><strong>Content:</strong> " + (content != null ? content : "Do not have") + "</p>"
                + "<p><strong>Metadata:</strong> " + (metadata != null ? metadata : "Do not have") + "</p>"
                + "<p style='margin-top: 20px;'>Best regards,<br/>Support team</p>"
                + "</div>";
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
    public List<NotificationResponse> getNotificationsByUserId(Long userId) {
        List<NotificationDelivery> deliveries = notificationDeliveryRepository.findByRecipientId(userId);

        return deliveries.stream()
                .map(NotificationDelivery::getNotification)
                .distinct()
                .map(notificationMapper::toNotificationResponse)
                .toList();
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }
        notificationRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateReadStatusBulk(BulkUpdateReadStatusRequest request) {
        List<Long> ids = request.getDeliveryIds().stream().map(Long::valueOf).toList();

        List<NotificationDelivery> deliveries = notificationDeliveryRepository.findAllById(ids);

        deliveries.forEach(delivery -> delivery.setRead(request.isRead()));

        notificationDeliveryRepository.saveAll(deliveries);
    }
}
