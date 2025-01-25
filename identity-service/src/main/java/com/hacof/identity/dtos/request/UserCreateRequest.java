package com.hacof.identity.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "EMAIL_IS_REQUIRED")
    @Pattern(regexp = "^[\\w._%+-]+@gmail\\.com$", message = "EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS")
    String email;

    @NotBlank(message = "PASSWORD_IS_REQUIRED")
    @Size(min = 3, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;
    String assignedRole;
}
