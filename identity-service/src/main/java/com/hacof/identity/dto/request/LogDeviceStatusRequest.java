package com.hacof.identity.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.hacof.identity.constant.DeviceQualityStatus;

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
public class LogDeviceStatusRequest {
    @Min(value = 1, message = "UserDevice ID must be greater than 0")
    long userDeviceId;

    @NotBlank(message = "Note cannot be empty")
    String note;

    @NotNull(message = "Device quality status is required")
    DeviceQualityStatus status;
}
