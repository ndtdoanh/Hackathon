package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.DeviceDTO;
import com.hacof.hackathon.entity.Device;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.DeviceMapper;
import com.hacof.hackathon.repository.DeviceRepository;
import com.hacof.hackathon.service.DeviceService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {
    final DeviceRepository deviceRepository;
    final DeviceMapper deviceMapper;

    @Override
    public DeviceDTO createDevice(DeviceDTO deviceDTO) {
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        return deviceMapper.toDTO(device);
    }

    @Override
    public DeviceDTO getDeviceById(Long id) {
        Device device =
                deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return deviceMapper.toDTO(device);
    }

    @Override
    public DeviceDTO updateDevice(Long id, DeviceDTO deviceDTO) {
        Device device =
                deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        device.setName(deviceDTO.getName());
        device.setDescription(deviceDTO.getDescription());
        device.setStatus(deviceDTO.getStatus());
        // device.setHackthon(deviceDTO.getHackathonId());
        device = deviceRepository.save(device);
        return deviceMapper.toDTO(device);
    }

    @Override
    public void deleteDevice(Long id) {
        Device device =
                deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        deviceRepository.delete(device);
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        return deviceRepository.findAll().stream().map(deviceMapper::toDTO).collect(Collectors.toList());
    }
}
