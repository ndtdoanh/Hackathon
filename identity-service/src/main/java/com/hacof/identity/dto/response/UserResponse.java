package com.hacof.identity.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.hacof.identity.constant.Status;

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
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    Boolean isVerified;
    Status status;
    Boolean noPassword;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdByUserId;
    UserProfileResponse userProfile;
    Set<RoleResponse> roles;

    public Boolean getIsVerified() {
        return isVerified != null ? isVerified : false;
    }

    public String getStatus() {
        return status != null ? status.name() : "ACTIVE";
    }
}
