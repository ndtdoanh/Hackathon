package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.Task;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDTO requestDTO, BoardList boardList, List<FileUrl> fileUrls) {
        Task task = Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .position(requestDTO.getPosition())
                .dueDate(requestDTO.getDueDate())
                .boardList(boardList)
                .build();

        task.setFileUrls(fileUrls);

        return task;
    }

    // Chuyển từ Task entity sang TaskResponseDTO
    public TaskResponseDTO toDto(Task task) {
        List<FileUrlResponse> fileUrls = task.getFileUrls() != null
                ? task.getFileUrls().stream()
                        .map(fileUrl -> FileUrlResponse.builder()
                                .id(String.valueOf(fileUrl.getId()))
                                .fileUrl(fileUrl.getFileUrl())
                                .fileName(fileUrl.getFileName())
                                .fileType(fileUrl.getFileType())
                                .fileSize(fileUrl.getFileSize())
                                .createdAt(fileUrl.getCreatedDate())
                                .updatedAt(fileUrl.getLastModifiedDate())
                                .build())
                        .collect(Collectors.toList())
                : null;

        return TaskResponseDTO.builder()
                .id(String.valueOf(task.getId()))
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .dueDate(task.getDueDate())
                .boardListId(
                        task.getBoardList() != null
                                ? String.valueOf(task.getBoardList().getId())
                                : null)
                .createdBy(task.getCreatedBy() != null ? task.getCreatedBy().getUsername() : null)
                .createdDate(task.getCreatedDate())
                .lastModifiedDate(task.getLastModifiedDate())
                .fileUrls(fileUrls)
                .build();
    }
}
