package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.DeviceDTO;
import com.hacof.hackathon.entity.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    DeviceDTO toDTO(Device device);

    Device toEntity(DeviceDTO deviceDTO);
}
