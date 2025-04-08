package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.entity.FileUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileUrlMapper {
    FileUrlMapper INSTANCE = Mappers.getMapper(FileUrlMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "fileUrl", source = "fileUrl")
    @Mapping(target = "fileType", source = "fileType")
    @Mapping(target = "fileSize", source = "fileSize")
    FileUrlResponse toResponse(FileUrl fileUrl);

    List<FileUrlResponse> toResponseList(List<FileUrl> fileUrls);
}