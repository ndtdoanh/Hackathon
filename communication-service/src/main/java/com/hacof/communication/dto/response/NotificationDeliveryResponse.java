package com.hacof.communication.dto.response;

import java.util.Collections;
import java.util.Set;

import com.hacof.communication.constant.NotificationMethod;
import com.hacof.communication.constant.NotificationStatus;
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
public class NotificationDeliveryResponse {
    String id;
    Set<UserResponse> recipients;
    RoleType role;
    NotificationMethod method;
    NotificationStatus status;

    public Set<UserResponse> getRecipients() {
        return recipients != null ? recipients : Collections.emptySet();
    }
}
