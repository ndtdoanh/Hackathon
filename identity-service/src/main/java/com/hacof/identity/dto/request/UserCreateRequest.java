package com.hacof.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
public class UserCreateRequest {

    @NotBlank(message = "USERNAME_IS_REQUIRED")
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "PASSWORD_IS_REQUIRED")
    @Size(min = 3, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;
    UserRoleRequest userRoles;
}
