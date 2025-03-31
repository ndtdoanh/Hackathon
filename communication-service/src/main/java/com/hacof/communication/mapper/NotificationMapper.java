package com.hacof.communication.mapper;

import com.hacof.communication.dto.response.NotificationDeliveryResponse;
import com.hacof.communication.dto.response.UserResponse;
import com.hacof.communication.entity.NotificationDelivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.entity.Notification;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(notification.getId()))")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "notificationType", source = "notificationType")
    @Mapping(target = "notificationDeliveries", source = "notificationDeliveries")
    NotificationResponse toNotificationResponse(Notification notification);

    @Mapping(target = "notificationId", source = "notification.id")
    @Mapping(target = "recipients", expression = "java(mapRecipients(delivery))")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    NotificationDeliveryResponse toNotificationDeliveryResponse(NotificationDelivery delivery);

    void updateNotificationFromRequest(NotificationRequest request, @MappingTarget Notification notification);

    default Set<UserResponse> mapRecipients(NotificationDelivery delivery) {
        if (delivery.getRecipient() != null) {
            return Collections.singleton(new UserResponse(
                    String.valueOf(delivery.getRecipient().getId()),
                    delivery.getRecipient().getUsername(),
                    delivery.getRecipient().getEmail(),
                    delivery.getRecipient().getFirstName(),
                    delivery.getRecipient().getLastName(),
                    delivery.getRecipient().getIsVerified(),
                    delivery.getRecipient().getStatus()
            ));
        }
        return Collections.emptySet();
    }

    default List<NotificationDeliveryResponse> notificationDeliveryListToNotificationDeliveryResponseList(List<NotificationDelivery> deliveries) {
        if (deliveries == null) {
            return Collections.emptyList();
        }
        return deliveries.stream()
                .map(this::toNotificationDeliveryResponse)
                .collect(Collectors.toList());
    }
}
