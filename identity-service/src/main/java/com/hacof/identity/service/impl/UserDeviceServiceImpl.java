package com.hacof.identity.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.identity.constant.UserDeviceStatus;
import com.hacof.identity.dto.request.AssignDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.entity.Device;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserDevice;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.UserDeviceMapper;
import com.hacof.identity.repository.DeviceRepository;
import com.hacof.identity.repository.UserDeviceRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.UserDeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceServiceImpl implements UserDeviceService {
    UserDeviceRepository userDeviceRepository;
    UserRepository userRepository;
    DeviceRepository deviceRepository;
    UserDeviceMapper userDeviceMapper;

    @Override
    public UserDeviceResponse assignDevice(AssignDeviceRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Device device = deviceRepository
                .findById(request.getDeviceId())
                .orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED));

        UserDevice userDevice = UserDevice.builder()
                .user(user)
                .device(device)
                .status(UserDeviceStatus.ASSIGNED)
                .timeFrom(LocalDateTime.now())
                .build();

        userDeviceRepository.save(userDevice);
        return userDeviceMapper.toUserDeviceResponse(userDevice);
    }

    @Override
    public List<UserDeviceResponse> getUserDevices() {
        return userDeviceRepository.findAll().stream()
                .map(userDeviceMapper::toUserDeviceResponse)
                .toList();
    }

    @Override
    public UserDeviceResponse getUserDevice(Long id) {
        UserDevice userDevice = userDeviceRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED));
        return userDeviceMapper.toUserDeviceResponse(userDevice);
    }
}
