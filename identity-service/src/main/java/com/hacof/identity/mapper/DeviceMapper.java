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

    @Mapping(target = "id", expression = "java(String.valueOf(device.getId()))")
    @Mapping(
            target = "createdByUserId",
            expression = "java(device.getCreatedBy() != null ? String.valueOf(device.getCreatedBy().getId()) : null)")
    DeviceResponse toDeviceResponse(Device device);

    @Mapping(target = "id", ignore = true)
    void updateDeviceFromRequest(DeviceRequest request, @MappingTarget Device device);
}
