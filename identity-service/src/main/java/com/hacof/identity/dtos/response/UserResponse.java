package com.hacof.identity.dtos.response;

import java.util.Set;

import com.hacof.identity.enums.Status;

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
    long id;
    String email;
    String firstName;
    String lastName;
    Boolean isVerified;
    Status status;
    Boolean noPassword;
    Set<RoleResponse> roles;

    public Boolean getIsVerified() {
        return isVerified != null ? isVerified : false;
    }

    public String getStatus() {
        return status != null ? status.name() : Status.ACTIVE.name();
    }
}
