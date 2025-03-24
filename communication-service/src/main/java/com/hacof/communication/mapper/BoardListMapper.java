package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.Board;
import org.springframework.stereotype.Component;

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
                .id(boardList.getId())
                .name(boardList.getName())
                .position(boardList.getPosition())
                .boardName(boardList.getBoard().getName())
                .createdBy(boardList.getCreatedBy() != null ? boardList.getCreatedBy().getUsername() : null)
                .createdDate(boardList.getCreatedDate())
                .lastModifiedDate(boardList.getLastModifiedDate())
                .build();
    }
}
