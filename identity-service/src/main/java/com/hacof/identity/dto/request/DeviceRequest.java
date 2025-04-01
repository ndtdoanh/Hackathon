package com.hacof.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.hacof.identity.constant.DeviceStatus;

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
public class DeviceRequest {
    String hackathonId;
    String roundId;
    String roundLocationId;

    @NotBlank(message = "Device name cannot be empty")
    @Size(max = 255, message = "Device name cannot exceed 255 characters")
    String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    DeviceStatus status;
}
