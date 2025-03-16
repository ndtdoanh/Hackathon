package com.hacof.identity.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.identity.dto.request.LogDeviceStatusRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.entity.UserDevice;
import com.hacof.identity.entity.UserDeviceTrack;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.UserDeviceTrackMapper;
import com.hacof.identity.repository.UserDeviceRepository;
import com.hacof.identity.repository.UserDeviceTrackRepository;
import com.hacof.identity.service.UserDeviceTrackService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceTrackServiceImpl implements UserDeviceTrackService {
    UserDeviceTrackRepository userDeviceTrackRepository;
    UserDeviceRepository userDeviceRepository;
    UserDeviceTrackMapper userDeviceTrackMapper;

    @Override
    public UserDeviceTrackResponse addDeviceTrack(LogDeviceStatusRequest request) {
        UserDevice userDevice = userDeviceRepository
                .findById(request.getUserDeviceId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED));

        UserDeviceTrack track = UserDeviceTrack.builder()
                .userDevice(userDevice)
                .deviceQualityStatus(request.getStatus())
                .note(request.getNote())
                .build();

        userDeviceTrackRepository.save(track);
        return userDeviceTrackMapper.toUserDeviceTrackResponse(track);
    }

    @Override
    public List<UserDeviceTrackResponse> getDeviceTracks() {
        return userDeviceTrackRepository.findAll().stream()
                .map(userDeviceTrackMapper::toUserDeviceTrackResponse)
                .toList();
    }
}
