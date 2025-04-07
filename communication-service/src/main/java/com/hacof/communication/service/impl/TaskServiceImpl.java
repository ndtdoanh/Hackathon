package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hacof.communication.dto.request.BulkTaskUpdateRequestDTO;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.repository.FileUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.Task;
import com.hacof.communication.mapper.TaskMapper;
import com.hacof.communication.repository.BoardListRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private FileUrlRepository fileUrlRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Long boardListId = Long.parseLong(taskRequestDTO.getBoardListId());
        Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);

        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        List<FileUrl> fileUrls = fileUrlRepository.findAllByFileUrlInAndTaskIsNull(taskRequestDTO.getFileUrls());
        Task task = taskMapper.toEntity(taskRequestDTO, boardListOptional.get(), fileUrls);

        task = taskRepository.save(task);

        for (FileUrl file : fileUrls) {
            file.setTask(task);
        }
        fileUrlRepository.saveAll(fileUrls);

        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        Task task = taskOptional.get();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());

        // Cập nhật file nếu có
        if (taskRequestDTO.getFileUrls() != null && !taskRequestDTO.getFileUrls().isEmpty()) {
            List<FileUrl> fileUrls = fileUrlRepository
                    .findAllByFileUrlInAndTaskIsNull(taskRequestDTO.getFileUrls()); // Tìm các file mà chưa được gắn vào bất kỳ task nào

            for (FileUrl file : fileUrls) {
                file.setTask(task); // Gán task cho file
            }

            fileUrlRepository.saveAll(fileUrls); // Lưu lại các file đã cập nhật
        }

        task = taskRepository.save(task); // Lưu lại task

        return taskMapper.toDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDTO getTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        return taskMapper.toDto(taskOptional.get());
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> updateBulkTasks(List<BulkTaskUpdateRequestDTO> bulkUpdateRequest) {
        List<TaskResponseDTO> updatedTasks = new ArrayList<>();

        for (BulkTaskUpdateRequestDTO updateRequest : bulkUpdateRequest) {
            // Parse taskId and boardListId from the request DTO


            // Check if the Task exists
            Optional<Task> taskOptional = taskRepository.findById(Long.valueOf(updateRequest.getId()));
            if (!taskOptional.isPresent()) {
                throw new IllegalArgumentException("Task with ID " + updateRequest.getId() + " not found!");
            }

            // Check if the BoardList exists
            Optional<BoardList> boardListOptional = boardListRepository.findById(Long.valueOf(updateRequest.getBoardListId()));
            if (!boardListOptional.isPresent()) {
                throw new IllegalArgumentException("BoardList with ID " + updateRequest.getBoardListId() + " not found!");
            }

            // Retrieve the Task and update the BoardList and Position
            Task task = taskOptional.get();
            task.setBoardList(boardListOptional.get()); // Update the board list for the task
            task.setPosition(updateRequest.getPosition()); // Update the position

            // Save the updated Task
            task = taskRepository.save(task);

            // Convert the updated task into a response DTO and add it to the response list
            TaskResponseDTO updatedTaskDTO = taskMapper.toDto(task);
            updatedTasks.add(updatedTaskDTO);
        }

        return updatedTasks;
    }

}
