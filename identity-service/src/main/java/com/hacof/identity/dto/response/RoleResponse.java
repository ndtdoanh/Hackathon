package com.hacof.identity.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

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
public class RoleResponse {
    String id;
    String name;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
    Set<PermissionResponse> permissions;
}
