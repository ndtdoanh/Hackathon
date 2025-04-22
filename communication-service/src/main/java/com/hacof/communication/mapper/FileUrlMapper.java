package com.hacof.communication.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.entity.FileUrl;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileUrlMapper {
    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(source = "lastModifiedDate", target = "updatedAt")
    FileUrlResponse toResponse(FileUrl fileUrl);

    List<FileUrlResponse> toResponseList(List<FileUrl> fileUrls);
}
