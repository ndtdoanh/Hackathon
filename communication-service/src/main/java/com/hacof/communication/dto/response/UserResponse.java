package com.hacof.communication.dto.response;

import com.hacof.communication.constant.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    Boolean isVerified;
    Status status;

    public Boolean getIsVerified() {
        return isVerified != null ? isVerified : false;
    }

    public String getStatus() {
        return status != null ? status.name() : "ACTIVE";
    }
}
