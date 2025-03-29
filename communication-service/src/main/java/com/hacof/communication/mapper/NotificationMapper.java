package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.entity.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(notification.getId()))")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "notificationType", source = "notificationType")
    NotificationResponse toNotificationResponse(Notification notification);

    void updateNotificationFromRequest(NotificationRequest request, @MappingTarget Notification notification);
}
