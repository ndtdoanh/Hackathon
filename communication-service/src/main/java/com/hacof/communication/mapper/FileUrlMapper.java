package com.hacof.communication.mapper;

import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.entity.FileUrl;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileUrlMapper {
    FileUrlResponse toResponse(FileUrl fileUrl);

    List<FileUrlResponse> toResponseList(List<FileUrl> fileUrls);
}
