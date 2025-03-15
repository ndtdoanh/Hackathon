package com.hacof.identity.service;

import java.util.List;

import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;

public interface DeviceService {

    DeviceResponse createDevice(DeviceRequest request);

    List<DeviceResponse> getDevices();

    DeviceResponse getDevice(Long id);

    DeviceResponse updateDevice(Long id, DeviceRequest request);

    void deleteDevice(Long id);
}
