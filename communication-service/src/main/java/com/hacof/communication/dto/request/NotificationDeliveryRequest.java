package com.hacof.communication.dto.request;

import java.util.Set;

import com.hacof.communication.constant.NotificationMethod;
import com.hacof.communication.constant.RoleType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDeliveryRequest {
    Set<Long> recipientIds;
    RoleType role;
    NotificationMethod method;
}
