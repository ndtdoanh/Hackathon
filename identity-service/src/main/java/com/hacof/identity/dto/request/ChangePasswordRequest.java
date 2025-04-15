package com.hacof.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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
