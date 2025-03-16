package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.DeviceDTO;

public interface DeviceService {
    DeviceDTO createDevice(DeviceDTO deviceDTO);

    DeviceDTO getDeviceById(Long id);

    DeviceDTO updateDevice(Long id, DeviceDTO deviceDTO);

    void deleteDevice(Long id);

    List<DeviceDTO> getAllDevices();
}
