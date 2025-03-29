package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dto.request.AssignDeviceRequest;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.entity.UserDevice;

@Mapper(componentModel = "spring")
public interface UserDeviceMapper {
    UserDevice toUserDevice(AssignDeviceRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "createdBy.id", target = "createdByUserId")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    UserDeviceResponse toUserDeviceResponse(UserDevice userDevice);
}
