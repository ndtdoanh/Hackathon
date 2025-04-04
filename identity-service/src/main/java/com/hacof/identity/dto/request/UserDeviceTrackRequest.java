package com.hacof.identity.dto.request;

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
public class UserDeviceTrackRequest {
    String userDeviceId;
    DeviceQualityStatus deviceQualityStatus;
    String note;
}
