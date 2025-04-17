package com.hacof.communication.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.dto.request.NotificationDeliveryRequest;
import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationDeliveryResponse;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.dto.response.UserResponse;
import com.hacof.communication.entity.Notification;
import com.hacof.communication.entity.NotificationDelivery;
import com.hacof.communication.entity.User;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(notification.getId()))")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "type", source = "type")
    @Mapping(
            target = "notificationDeliveries",
            expression = "java(mapNotificationDeliveries(notification.getNotificationDeliveries()))")
    NotificationResponse toNotificationResponse(Notification notification);

    @Mapping(target = "id", expression = "java(String.valueOf(delivery.getId()))")
    @Mapping(target = "recipients", expression = "java(mapRecipients(delivery))")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "method", source = "method")
    @Mapping(target = "status", source = "status")
    NotificationDeliveryResponse toNotificationDeliveryResponse(NotificationDelivery delivery);

    default Notification toNotification(NotificationRequest request, User sender) {
        return Notification.builder()
                .sender(sender)
                .type(request.getType())
                .content(request.getContent())
                .metadata(request.getMetadata())
                .build();
    }

    default List<NotificationDelivery> toNotificationDeliveries(
            Notification notification, Set<User> recipients, NotificationDeliveryRequest request) {
        return recipients.stream()
                .map(user -> NotificationDelivery.builder()
                        .notification(notification)
                        .recipient(user)
                        .role(request.getRole())
                        .method(request.getMethod())
                        .status(NotificationStatus.SENT)
                        .isRead(false)
                        .build())
                .collect(Collectors.toList());
    }

    default Set<UserResponse> mapRecipients(NotificationDelivery delivery) {
        return delivery.getRecipient() != null
                ? Collections.singleton(new UserResponse(
                        String.valueOf(delivery.getRecipient().getId()),
                        delivery.getRecipient().getUsername(),
                        delivery.getRecipient().getEmail(),
                        delivery.getRecipient().getFirstName(),
                        delivery.getRecipient().getLastName(),
                        delivery.getRecipient().getAvatarUrl()))
                : Collections.emptySet();
    }

    default List<NotificationDeliveryResponse> mapNotificationDeliveries(List<NotificationDelivery> deliveries) {
        return deliveries == null
                ? Collections.emptyList()
                : deliveries.stream().map(this::toNotificationDeliveryResponse).collect(Collectors.toList());
    }
}
