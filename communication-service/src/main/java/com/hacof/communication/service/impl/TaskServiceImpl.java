package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BulkTaskUpdateRequestDTO;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.Task;
import com.hacof.communication.mapper.FileUrlMapper;
import com.hacof.communication.mapper.TaskMapper;
import com.hacof.communication.repository.BoardListRepository;
import com.hacof.communication.repository.FileUrlRepository;
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

    @Autowired
    private FileUrlMapper fileUrlMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.getBoardListId() == null
                || taskRequestDTO.getBoardListId().isEmpty()) {
            throw new IllegalArgumentException("BoardList ID cannot be null or empty");
        }

        Long boardListId = Long.parseLong(taskRequestDTO.getBoardListId());
        Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList with ID " + taskRequestDTO.getBoardListId() + " not found!");
        }

        if (taskRequestDTO.getTitle() == null || taskRequestDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        if (taskRequestDTO.getDescription() == null
                || taskRequestDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }

        if (taskRequestDTO.getPosition() < 0) {
            throw new IllegalArgumentException("Position must be a non-negative integer");
        }

        //        if (taskRequestDTO.getDueDate() == null) {
        //            throw new IllegalArgumentException("Due date must be a future date");
        //        }

        Task task = taskMapper.toEntity(taskRequestDTO, boardListOptional.get(), null); // Không truyền fileUrls
        task = taskRepository.save(task);

        return taskMapper.toDto(task);
    }

    //    @Override
    //    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
    //        Optional<Task> taskOptional = taskRepository.findById(id);
    //        if (!taskOptional.isPresent()) {
    //            throw new IllegalArgumentException("Task with ID " + id + " not found!");
    //        }
    //
    //        if (taskRequestDTO.getBoardListId() == null
    //                || taskRequestDTO.getBoardListId().isEmpty()) {
    //            throw new IllegalArgumentException("BoardList ID cannot be null or empty");
    //        }
    //        Long boardListId = Long.parseLong(taskRequestDTO.getBoardListId());
    //        Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);
    //        if (!boardListOptional.isPresent()) {
    //            throw new IllegalArgumentException("BoardList with ID " + taskRequestDTO.getBoardListId() + " not
    // found!");
    //        }
    //
    //        if (taskRequestDTO.getTitle() == null || taskRequestDTO.getTitle().isEmpty()) {
    //            throw new IllegalArgumentException("Task title cannot be empty");
    //        }
    //
    //        if (taskRequestDTO.getDescription() == null
    //                || taskRequestDTO.getDescription().isEmpty()) {
    //            throw new IllegalArgumentException("Task description cannot be empty");
    //        }
    //
    //        if (taskRequestDTO.getPosition() < 0) {
    //            throw new IllegalArgumentException("Position must be a non-negative integer");
    //        }
    //
    //        if (taskRequestDTO.getFileUrls() != null
    //                && !taskRequestDTO.getFileUrls().isEmpty()) {
    //            List<FileUrl> fileUrls =
    // fileUrlRepository.findAllByFileUrlInAndTaskIsNull(taskRequestDTO.getFileUrls());
    //            if (fileUrls.size() != taskRequestDTO.getFileUrls().size()) {
    //                throw new IllegalArgumentException(
    //                        "Some file URLs are invalid or already associated with other tasks.");
    //            }
    //
    //            for (FileUrl file : fileUrls) {
    //                file.setTask(taskOptional.get());
    //            }
    //            fileUrlRepository.saveAll(fileUrls);
    //        }
    //
    //        Task task = taskOptional.get();
    //        task.setTitle(taskRequestDTO.getTitle());
    //        task.setDescription(taskRequestDTO.getDescription());
    //        task.setDueDate(taskRequestDTO.getDueDate());
    //
    //        task = taskRepository.save(task);
    //
    //        return taskMapper.toDto(task);
    //    }

    @Override
    public TaskResponseDTO updateTaskInfo(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");
        }

        // Validate and update task info
        if (taskRequestDTO.getTitle() == null || taskRequestDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        if (taskRequestDTO.getDescription() == null
                || taskRequestDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }

        Task task = taskOptional.get();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());

        task = taskRepository.save(task);

        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDTO updateTaskFiles(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");
        }

        Task task = taskOptional.get();

        // If fileUrls are provided, process them
        if (taskRequestDTO.getFileUrls() != null
                && !taskRequestDTO.getFileUrls().isEmpty()) {
            // Fetch the fileUrls that exist in the DB and are not already linked to another task
            List<FileUrl> fileUrls = fileUrlRepository.findAllByFileUrlInAndTaskIsNull(taskRequestDTO.getFileUrls());

            if (fileUrls.size() != taskRequestDTO.getFileUrls().size()) {
                throw new IllegalArgumentException(
                        "Some file URLs are invalid or already associated with other tasks.");
            }

            // Optionally remove old fileUrls if they should be replaced, or merge
            // For example, remove old fileUrls or clear and add only new ones
            // task.getFileUrls().clear();  // If you want to completely replace the fileUrls

            // Add new fileUrls to the task
            for (FileUrl file : fileUrls) {
                if (!task.getFileUrls().contains(file)) {
                    task.getFileUrls().add(file); // Add only new fileUrls
                    file.setTask(task);
                }
            }

            // Save the task with the new fileUrls
            fileUrlRepository.saveAll(fileUrls);

            // Save the task
            task = taskRepository.save(task);

            return taskMapper.toDto(task);
        }

        throw new IllegalArgumentException("No file URLs provided for update");
    }

    @Override
    public void deleteTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");
        }

        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDTO getTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");
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
            Long taskId = Long.parseLong(updateRequest.getId());
            Long boardListId = Long.parseLong(updateRequest.getBoardListId());

            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (!taskOptional.isPresent()) {
                throw new IllegalArgumentException("Task with ID " + updateRequest.getId() + " not found!");
            }

            Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);
            if (!boardListOptional.isPresent()) {
                throw new IllegalArgumentException(
                        "BoardList with ID " + updateRequest.getBoardListId() + " not found!");
            }

            Task task = taskOptional.get();
            task.setBoardList(boardListOptional.get());
            task.setPosition(updateRequest.getPosition());

            task = taskRepository.save(task);

            TaskResponseDTO updatedTaskDTO = taskMapper.toDto(task);
            updatedTasks.add(updatedTaskDTO);
        }

        return updatedTasks;
    }

    @Override
    public List<TaskResponseDTO> getTasksByBoardListId(Long boardListId) {
        Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList with ID " + boardListId + " not found!");
        }
        List<Task> tasks = taskRepository.findByBoardListId(boardListId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FileUrlResponse> getFileUrlsByTaskId(Long taskId) {
        Task task =
                taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("TaskId not found!"));
        return fileUrlMapper.toResponseList(task.getFileUrls());
    }
}
