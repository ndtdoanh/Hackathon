package com.hacof.identity.dto.response;

import com.hacof.identity.constant.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    Boolean isVerified;
    Status status;
    Boolean noPassword;
    String phone;
    String bio;
    Set<String> skills;
    String avatarUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
    Set<UserRoleResponse> userRoles;
    Set<UserHackathonResponse> userHackathons;
    Set<UserTeamResponse> userTeams;

    public Boolean getIsVerified() {
        return isVerified != null ? isVerified : false;
    }

    public String getStatus() {
        return status != null ? status.name() : "ACTIVE";
    }
}
