package com.hacof.identity.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.identity.constant.DeviceStatus;
import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.entity.Device;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.DeviceMapper;
import com.hacof.identity.repository.DeviceRepository;
import com.hacof.identity.service.DeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceServiceImpl implements DeviceService {

    DeviceRepository deviceRepository;
    DeviceMapper deviceMapper;

    @Override
    public DeviceResponse createDevice(DeviceRequest request) {
        if (deviceRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.DEVICE_EXISTED);
        }

        Device device = deviceMapper.toDevice(request);

        if (device.getHackathon() != null) {
            device.setStatus(DeviceStatus.AVAILABLE);
        } else {
            device.setStatus(DeviceStatus.PENDING);
        }

        deviceRepository.save(device);
        return deviceMapper.toDeviceResponse(device);
    }

    @Override
    public List<DeviceResponse> getDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toDeviceResponse)
                .toList();
    }

    @Override
    public DeviceResponse getDevice(Long id) {
        return deviceMapper.toDeviceResponse(
                deviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED)));
    }

    @Override
    public DeviceResponse updateDevice(Long id, DeviceRequest request) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED));

        deviceMapper.updateDeviceFromRequest(request, device);
        device = deviceRepository.save(device);

        return deviceMapper.toDeviceResponse(device);
    }

    @Override
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new AppException(ErrorCode.DEVICE_NOT_EXISTED);
        }
        deviceRepository.deleteById(id);
    }
}
