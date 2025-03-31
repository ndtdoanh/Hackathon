package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dto.request.AssignDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.entity.UserDevice;

@Mapper(componentModel = "spring")
public interface UserDeviceMapper {
    UserDevice toUserDevice(AssignDeviceRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(userDevice.getId()))")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(
            target = "createdByUserName",
            expression = "java(userDevice.getCreatedBy() != null ? userDevice.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    UserDeviceResponse toUserDeviceResponse(UserDevice userDevice);
}
