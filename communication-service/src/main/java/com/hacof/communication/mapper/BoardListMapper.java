package com.hacof.communication.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.Task;

@Component
public class BoardListMapper {

    // Chuyển từ BoardListRequestDTO sang BoardList entity
    public BoardList toEntity(BoardListRequestDTO requestDTO, Board board) {
        return BoardList.builder()
                .name(requestDTO.getName())
                .position(requestDTO.getPosition())
                .board(board)
                .build();
    }

    // Chuyển từ BoardList entity sang BoardListResponseDTO
    public BoardListResponseDTO toDto(BoardList boardList) {
        return BoardListResponseDTO.builder()
                .id(String.valueOf(boardList.getId()))
                .name(boardList.getName())
                .position(boardList.getPosition())
                .boardId(String.valueOf(boardList.getBoard().getId()))
                //                .board(boardList.getBoard() != null ? mapBoardToDto(boardList.getBoard()) : null) //
                // Map Board
                .tasks(boardList.getTasks() != null ? mapTasksToDto(boardList.getTasks()) : null) // Map Tasks
                .createdBy(
                        boardList.getCreatedBy() != null
                                ? boardList.getCreatedBy().getUsername()
                                : null)
                .createdDate(boardList.getCreatedDate())
                .lastModifiedDate(boardList.getLastModifiedDate())
                .build();
    }

    // Chuyển đổi Board sang BoardResponseDTO
    private BoardResponseDTO mapBoardToDto(Board board) {
        return BoardResponseDTO.builder()
                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .description(board.getDescription()) // Lấy mô tả Board
                .ownerId(String.valueOf(board.getOwner().getId()))
                .teamId(String.valueOf(board.getTeam().getId()))
                .hackathonId(String.valueOf(board.getHackathon().getId())) // Lấy tên team (nếu có)
                .createdBy(board.getCreatedBy() != null ? board.getCreatedBy().getUsername() : null) // Người tạo
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }

    // Chuyển đổi danh sách Task sang danh sách TaskResponseDTO
    private List<TaskResponseDTO> mapTasksToDto(Set<Task> tasks) {
        return tasks.stream().map(this::mapTaskToDto).collect(Collectors.toList());
    }

    // Chuyển đổi Task sang TaskResponseDTO
    private TaskResponseDTO mapTaskToDto(Task task) {
        return TaskResponseDTO.builder()
                .id(String.valueOf(task.getId()))
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .dueDate(task.getDueDate())
                .createdBy(task.getCreatedBy() != null ? task.getCreatedBy().getUsername() : null)
                .createdDate(task.getCreatedDate())
                .lastModifiedDate(task.getLastModifiedDate())
                .build();
    }
}
