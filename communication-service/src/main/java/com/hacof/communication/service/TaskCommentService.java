package com.hacof.communication.service;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;

import java.util.List;

public interface TaskCommentService {

    TaskCommentResponseDTO createTaskComment(TaskCommentRequestDTO taskCommentRequestDTO);
}
