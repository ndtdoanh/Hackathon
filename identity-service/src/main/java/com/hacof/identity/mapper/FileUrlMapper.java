package com.hacof.identity.mapper;

import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.entity.Device;
import com.hacof.identity.entity.FileUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileUrlMapper {
    FileUrlResponse toResponse(FileUrl fileUrl);

    List<FileUrlResponse> toResponseList(List<FileUrl> fileUrls);

    @Mapping(target = "id", expression = "java(String.valueOf(device.getId()))")
    @Mapping(source = "hackathon.id", target = "hackathonId")
    @Mapping(source = "round.id", target = "roundId")
    @Mapping(source = "roundLocation.id", target = "roundLocationId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "fileUrls", target = "fileUrls")
    @Mapping(
            target = "createdByUserName",
            expression = "java(device.getCreatedBy() != null ? device.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    DeviceResponse toDeviceResponse(Device device);
}
