package com.hacof.identity.dto.request;

import jakarta.validation.constraints.Min;

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
public class AssignDeviceRequest {
    @Min(value = 1, message = "User ID must be greater than 0")
    long userId;

    @Min(value = 1, message = "Device ID must be greater than 0")
    long deviceId;
}
