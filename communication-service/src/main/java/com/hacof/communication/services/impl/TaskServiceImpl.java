package com.hacof.communication.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.MoveTaskRequest;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entities.Mentor;
import com.hacof.communication.entities.Task;
import com.hacof.communication.entities.User;
import com.hacof.communication.repositories.MentorRepository;
import com.hacof.communication.repositories.TaskRepository;
import com.hacof.communication.repositories.UserRepository;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.TaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;

    @Override
    public CommonResponse<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks =
                taskRepository.findAll().stream().map(TaskResponseDTO::new).collect(Collectors.toList());
        return new CommonResponse<>(HttpStatus.OK.value(), "Success", tasks, null);
    }

    @Override
    public CommonResponse<TaskResponseDTO> getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            TaskResponseDTO taskResponseDTO =
                    new TaskResponseDTO(task); // Assuming TaskResponseDTO has constructor taking Task
            return new CommonResponse<>(HttpStatus.OK.value(), "Success", taskResponseDTO, null);
        } else {
            throw new RuntimeException("Task with id " + id + " not found!");
        }
    }

    @Override
    public CommonResponse<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO) {
        User assignedUser = userRepository
                .findById(taskRequestDTO.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        Mentor mentor = mentorRepository
                .findById(taskRequestDTO.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        Task task = new Task();
        task.setName(taskRequestDTO.getName());
        task.setDescription(taskRequestDTO.getDescription());
        task.setComment(taskRequestDTO.getComment());
        task.setDocumentUrl(taskRequestDTO.getDocumentUrl());
        task.setStatus(taskRequestDTO.getStatus());
        task.setPriority(taskRequestDTO.getPriority());
        task.setDeadline(taskRequestDTO.getDeadline());
        task.setListName(taskRequestDTO.getListName());
        task.setBoardName(taskRequestDTO.getBoardName());
        task.setAssignedTo(assignedUser);
        task.setMentor(mentor);

        Task savedTask = taskRepository.save(task);

        return new CommonResponse<>(
                HttpStatus.CREATED.value(), "Task created successfully", new TaskResponseDTO(savedTask), null);
    }

    @Override
    public CommonResponse<TaskResponseDTO> updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setName(taskRequestDTO.getName());
            task.setDescription(taskRequestDTO.getDescription());
            task.setComment(taskRequestDTO.getComment());
            task.setDocumentUrl(taskRequestDTO.getDocumentUrl());
            task.setStatus(taskRequestDTO.getStatus());
            task.setPriority(taskRequestDTO.getPriority());
            task.setDeadline(taskRequestDTO.getDeadline());
            task.setListName(taskRequestDTO.getListName());
            task.setBoardName(taskRequestDTO.getBoardName());

            Task updatedTask = taskRepository.save(task);
            return new CommonResponse<>(
                    HttpStatus.OK.value(), "Task updated successfully!", new TaskResponseDTO(updatedTask), null);
        } else {
            throw new RuntimeException("Task with id " + id + " not found!");
        }
    }

    @Override
    public CommonResponse<String> deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Task with id " + id + " not found", null, null);
        }
        taskRepository.deleteById(id);
        return new CommonResponse<>(HttpStatus.OK.value(), "Task deleted successfully", "Deleted task ID: " + id, null);
    }

    @Override
    public CommonResponse<String> moveTask(Long taskId, MoveTaskRequest moveTaskRequest) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setListName(moveTaskRequest.getListName());
            taskRepository.save(task);

            return new CommonResponse<>(
                    HttpStatus.OK.value(),
                    "Task moved successfully",
                    "Task moved to: " + moveTaskRequest.getListName(),
                    null);
        } else {
            throw new RuntimeException("Task with id " + taskId + " not found!");
        }
    }
}
