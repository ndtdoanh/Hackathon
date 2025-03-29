package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.hacof.communication.constant.Status;
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

    // Đảm bảo giá trị mặc định nếu bị null
    public Boolean getIsVerified() {
        return isVerified != null ? isVerified : false;
    }

    public String getStatus() {
        return status != null ? status.name() : "ACTIVE";
    }
}
