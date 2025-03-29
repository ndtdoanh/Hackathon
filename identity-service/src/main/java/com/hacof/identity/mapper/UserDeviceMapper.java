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
            target = "createdByUserId",
            expression =
                    "java(userDevice.getCreatedBy() != null ? String.valueOf(userDevice.getCreatedBy().getId()) : null)")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    UserDeviceResponse toUserDeviceResponse(UserDevice userDevice);
}
