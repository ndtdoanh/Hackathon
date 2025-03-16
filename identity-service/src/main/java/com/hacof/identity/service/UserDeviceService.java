package com.hacof.identity.service;

import java.util.List;

import com.hacof.identity.dto.request.AssignDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;

public interface UserDeviceService {

    UserDeviceResponse assignDevice(AssignDeviceRequest request);

    List<UserDeviceResponse> getUserDevices();

    UserDeviceResponse getUserDevice(Long id);
}
