package com.hacof.submission.dto.response;

import com.hacof.submission.constant.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
