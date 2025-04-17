package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.entity.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "status", source = "status")
    Device toDevice(DeviceRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(device.getId()))")
    @Mapping(target = "hackathonId", source = "hackathon.id")
    @Mapping(target = "roundId", source = "round.id")
    @Mapping(target = "roundLocationId", source = "roundLocation.id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "fileUrls", source = "fileUrls")
    @Mapping(
            target = "createdByUserName",
            expression = "java(device.getCreatedBy() != null ? device.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    DeviceResponse toDeviceResponse(Device device);

    @Mapping(target = "id", ignore = true)
    @Mapping(
            target = "hackathon.id",
            source = "hackathonId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "round.id",
            source = "roundId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "roundLocation.id",
            source = "roundLocationId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "name",
            source = "name",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "description",
            source = "description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "quantity",
            source = "quantity",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "status",
            source = "status",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeviceFromRequest(DeviceRequest request, @MappingTarget Device device);
}
