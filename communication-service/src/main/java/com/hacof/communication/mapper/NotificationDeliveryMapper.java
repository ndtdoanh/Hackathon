package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.response.NotificationDeliveryResponse;
import com.hacof.communication.entity.NotificationDelivery;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface NotificationDeliveryMapper {

    @Mapping(source = "method", target = "method")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "recipient", target = "recipients", qualifiedByName = "mapRecipientToRecipients")
    NotificationDeliveryResponse toNotificationDeliveryResponse(NotificationDelivery delivery);
}
