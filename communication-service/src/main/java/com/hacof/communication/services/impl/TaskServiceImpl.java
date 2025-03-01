package com.hacof.communication.services.impl;

import com.hacof.communication.dto.request.MoveTaskRequest;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entities.Mentor;
import com.hacof.communication.entities.Task;
import com.hacof.communication.entities.User;
import com.hacof.communication.repositories.MentorRepository;
import com.hacof.communication.repositories.TaskRepository;
import com.hacof.communication.repositories.UserRepository;
import com.hacof.communication.services.TaskService;
import com.hacof.communication.responses.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;

    @Override
    public CommonResponse<List<TaskResponseDTO>> getAllTasks() {
        // Sử dụng TaskResponseDTO từ Task entity
        List<TaskResponseDTO> tasks = taskRepository.findAll().stream()
                .map(TaskResponseDTO::new)
                .collect(Collectors.toList());
        return new CommonResponse<>(HttpStatus.OK.value(), "Success", tasks, null);
    }

    @Override
    public CommonResponse<TaskResponseDTO> getTaskById(Long id) {
        // Lấy task theo ID và chuyển đổi sang TaskResponseDTO
        Optional<Task> task = taskRepository.findById(id);
        return task.map(value -> new CommonResponse<>(HttpStatus.OK.value(), "Success", new TaskResponseDTO(value), null))
                .orElseGet(() -> new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Task not found", null, List.of("Task with id " + id + " not found")));
    }

    @Override
    public CommonResponse<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO) {
        // Lấy user và mentor từ database bằng ID
        User assignedUser = userRepository.findById(taskRequestDTO.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        Mentor mentor = mentorRepository.findById(taskRequestDTO.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        // Tạo task mới từ DTO
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

        // Thiết lập assignedTo và mentor
        task.setAssignedTo(assignedUser);
        task.setMentor(mentor);

        // Lưu task vào database
        Task savedTask = taskRepository.save(task);

        return new CommonResponse<>(HttpStatus.CREATED.value(), "Task created successfully", new TaskResponseDTO(savedTask), null);
    }


    @Override
    public CommonResponse<TaskResponseDTO> updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        // Tìm và cập nhật task theo ID
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(taskRequestDTO.getName());
                    existingTask.setDescription(taskRequestDTO.getDescription());
                    existingTask.setComment(taskRequestDTO.getComment());
                    existingTask.setDocumentUrl(taskRequestDTO.getDocumentUrl());
                    existingTask.setStatus(taskRequestDTO.getStatus());
                    existingTask.setPriority(taskRequestDTO.getPriority());
                    existingTask.setDeadline(taskRequestDTO.getDeadline());
                    existingTask.setListName(taskRequestDTO.getListName());
                    existingTask.setBoardName(taskRequestDTO.getBoardName());

                    // Lưu task đã cập nhật
                    Task updatedTask = taskRepository.save(existingTask);
                    return new CommonResponse<>(HttpStatus.OK.value(), "Task updated successfully", new TaskResponseDTO(updatedTask), null);
                })
                .orElseGet(() -> new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Task not found", null, List.of("Task with id " + id + " not found")));
    }

    @Override
    public CommonResponse<String> deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Task not found", null, List.of("Task with id " + id + " not found"));
        }

        taskRepository.deleteById(id);
        return new CommonResponse<>(HttpStatus.OK.value(), "Task deleted successfully", "Deleted task ID: " + id, null);
    }

    @Override
    public CommonResponse<String> moveTask(Long taskId, MoveTaskRequest moveTaskRequest) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            return new CommonResponse<>(HttpStatus.NOT_FOUND.value(), "Task not found", null, null);
        }

        Task task = taskOptional.get();
        task.setListName(moveTaskRequest.getListName());
        taskRepository.save(task);

        return new CommonResponse<>(HttpStatus.OK.value(), "Task moved successfully", "Task moved to: " + moveTaskRequest.getListName(), null);
    }
}
