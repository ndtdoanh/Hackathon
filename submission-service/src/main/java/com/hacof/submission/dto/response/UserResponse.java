package com.hacof.submission.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.hacof.submission.constant.Status;
import com.hacof.submission.entity.User;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isVerified;
    private Status status;
    private Boolean noPassword;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long createdByUserId;
    private Set<RoleResponse> roles;

    // Constructor to map from User entity
    public UserResponse(User user) {
        if (user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.isVerified = user.getIsVerified();
            this.status = user.getStatus();
            this.createdDate = user.getCreatedDate();
            this.lastModifiedDate = user.getLastModifiedDate();
            this.createdByUserId =
                    user.getCreatedBy() != null ? user.getCreatedBy().getId() : null;
        }
    }

    // Ensure default values if null
    public Boolean getIsVerified() {
        return isVerified != null ? isVerified : false;
    }

    public Status getStatus() {
        return status != null ? status : Status.ACTIVE;
    }
}
