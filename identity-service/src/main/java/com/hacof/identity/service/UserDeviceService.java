package com.hacof.identity.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.UserDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;

public interface UserDeviceService {

    UserDeviceResponse createUserDevice(UserDeviceRequest request, List<MultipartFile> files) throws IOException;

    List<UserDeviceResponse> getUserDevices();

    UserDeviceResponse getUserDevice(Long id);

    List<UserDeviceResponse> getUserDevicesByDeviceId(String deviceId);

    List<UserDeviceResponse> getUserDevicesByUserId(String userId);

    UserDeviceResponse updateUserDevice(Long id, UserDeviceRequest request, List<MultipartFile> files)
            throws IOException;

    void deleteUserDevice(Long id);
}
