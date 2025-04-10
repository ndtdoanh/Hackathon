package com.hacof.identity.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.dto.response.FileUrlResponse;

public interface DeviceService {

    DeviceResponse createDevice(DeviceRequest request, List<MultipartFile> files) throws IOException;

    List<DeviceResponse> getDevices();

    DeviceResponse getDevice(Long id);

    List<DeviceResponse> getDevicesByRoundId(String roundId);

    List<DeviceResponse> getDevicesByRoundLocationId(String roundLocationId);

    List<FileUrlResponse> getFileUrlsByDeviceId(Long deviceId);

    FileUrlResponse getFileUrlById(Long id);

    DeviceResponse updateDevice(Long id, DeviceRequest request, List<MultipartFile> files) throws IOException;

    void deleteDevice(Long id);
}
