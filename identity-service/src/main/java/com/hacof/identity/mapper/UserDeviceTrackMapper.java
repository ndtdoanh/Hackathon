package com.hacof.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.identity.dto.request.LogDeviceStatusRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.entity.UserDeviceTrack;

@Mapper(componentModel = "spring")
public interface UserDeviceTrackMapper {
    UserDeviceTrack toUserDeviceTrack(LogDeviceStatusRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(userDeviceTrack.getId()))")
    @Mapping(
            target = "userDeviceId",
            expression =
                    "java(userDeviceTrack.getUserDevice() != null ? String.valueOf(userDeviceTrack.getUserDevice().getId()) : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    UserDeviceTrackResponse toUserDeviceTrackResponse(UserDeviceTrack userDeviceTrack);
}
