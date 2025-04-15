package com.hacof.identity.mapper;

import com.hacof.identity.dto.request.UserDeviceTrackRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.entity.UserDeviceTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserDeviceTrackMapper {

    @Mapping(target = "userDevice.id", source = "userDeviceId")
    @Mapping(target = "deviceQualityStatus", source = "deviceQualityStatus")
    @Mapping(target = "note", source = "note")
    UserDeviceTrack toUserDeviceTrack(UserDeviceTrackRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(userDeviceTrack.getId()))")
    @Mapping(target = "userDeviceId", source = "userDevice.id")
    @Mapping(target = "deviceQualityStatus", source = "deviceQualityStatus")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "fileUrls", source = "fileUrls")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    UserDeviceTrackResponse toUserDeviceTrackResponse(UserDeviceTrack userDeviceTrack);

    @Mapping(
            target = "userDevice.id",
            source = "userDeviceId",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "deviceQualityStatus",
            source = "deviceQualityStatus",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            target = "note",
            source = "note",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserDeviceTrack(UserDeviceTrackRequest request, @MappingTarget UserDeviceTrack userDeviceTrack);
}
