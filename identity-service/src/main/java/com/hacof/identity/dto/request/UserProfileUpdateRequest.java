package com.hacof.identity.dto.request;

import java.util.Set;

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
public class UserProfileUpdateRequest {
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    String phoneNumber;

    @Size(min = 1, message = "At least one skill is required")
    Set<@NotBlank(message = "Skill cannot be blank") String> skills;
}
