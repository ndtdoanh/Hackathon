package com.hacof.identity.service;

import java.util.List;

import com.hacof.identity.dto.request.LogDeviceStatusRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;

public interface UserDeviceTrackService {

    UserDeviceTrackResponse addDeviceTrack(LogDeviceStatusRequest request);

    List<UserDeviceTrackResponse> getDeviceTracks();
}
