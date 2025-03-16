package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dto.request.LogDeviceStatusRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.entity.UserDeviceTrack;

@Mapper(componentModel = "spring")
public interface UserDeviceTrackMapper {
    UserDeviceTrack toUserDeviceTrack(LogDeviceStatusRequest request);

    @Mapping(source = "userDevice.id", target = "userDeviceId")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    UserDeviceTrackResponse toUserDeviceTrackResponse(UserDeviceTrack userDeviceTrack);
}
