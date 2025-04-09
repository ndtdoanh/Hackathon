package com.hacof.communication.mapper;


import java.util.List;

import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.ScheduleEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface    FileUrlMapper {
    FileUrlResponse toResponse(FileUrl fileUrl);

    List<FileUrlResponse> toResponseList(List<FileUrl> fileUrls);
}
