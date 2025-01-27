package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.TaskDTO;
import com.hacof.hackathon.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO convertToDTO(Task task);

    Task convertToEntity(TaskDTO taskDTO);
}
