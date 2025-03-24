package com.hacof.communication.service;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;

import java.util.List;

public interface TaskCommentService {

    TaskCommentResponseDTO createTaskComment(TaskCommentRequestDTO taskCommentRequestDTO);

    TaskCommentResponseDTO updateTaskComment(Long id, TaskCommentRequestDTO taskCommentRequestDTO);

    void deleteTaskComment(Long id);

    TaskCommentResponseDTO getTaskComment(Long id);

}
