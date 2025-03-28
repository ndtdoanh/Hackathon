package com.hacof.identity.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    Long userId;
    String phone;
    String bio;
    Set<String> skills;
    String avatarUrl;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
