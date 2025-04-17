package com.hacof.communication.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.entity.FileUrl;

@Mapper(componentModel = "spring")
public interface FileUrlMapper {
    FileUrlResponse toResponse(FileUrl fileUrl);

    List<FileUrlResponse> toResponseList(List<FileUrl> fileUrls);
}
