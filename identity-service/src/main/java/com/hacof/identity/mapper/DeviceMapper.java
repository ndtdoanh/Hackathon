package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.entity.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    Device toDevice(DeviceRequest request);

    DeviceResponse toDeviceResponse(Device device);

    @Mapping(target = "id", ignore = true)
    void updateDeviceFromRequest(DeviceRequest request, @MappingTarget Device device);
}
