package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.identity.dto.request.UserDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.entity.UserDevice;

@Mapper(componentModel = "spring")
public interface UserDeviceMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "device.id", source = "deviceId")
    @Mapping(target = "timeFrom", source = "timeFrom")
    @Mapping(target = "timeTo", source = "timeTo")
    @Mapping(target = "status", source = "status")
    UserDevice toUserDevice(UserDeviceRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(userDevice.getId()))")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "deviceId", source = "device.id")
    @Mapping(target = "timeFrom", source = "timeFrom")
    @Mapping(target = "timeTo", source = "timeTo")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "fileUrls", source = "fileUrls")
    @Mapping(
            target = "createdByUserName",
            expression = "java(userDevice.getCreatedBy() != null ? userDevice.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    UserDeviceResponse toUserDeviceResponse(UserDevice userDevice);

    @Mapping(target = "id", ignore = true)
    @Mapping(
            target = "timeFrom",
            source = "timeFrom",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "timeTo",
            source = "timeTo",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "status",
            source = "status",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserDeviceFromRequest(UserDeviceRequest request, @MappingTarget UserDevice userDevice);
}
