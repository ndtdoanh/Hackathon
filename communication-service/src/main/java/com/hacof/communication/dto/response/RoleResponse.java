package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    long id;
    String name;
    String description;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    Long createdByUserId;
    Set<PermissionResponse> permissions;
}