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
public class ChangePasswordRequest {

    @NotBlank(message = "The current password is not allowed to leave blank")
    String currentPassword;

    @NotBlank(message = "The new password must not leave blank")
    @Size(min = 3, message = "Password must have at least 3 characters")
    String newPassword;

    @NotBlank(message = "Confirm the password must not leave blank")
    String confirmPassword;
}
