package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.Team;
import com.hacof.communication.entity.User;

@Component
public class BoardMapper {
    // Chuyển đổi từ BoardRequestDTO sang Board entity
    public Board toEntity(BoardRequestDTO boardRequestDTO, User owner, Team team) {
        return Board.builder()
                .name(boardRequestDTO.getName())
                .description(boardRequestDTO.getDescription())
                .owner(owner)
                .team(team)
                .build();
    }

    // Chuyển đổi từ Board entity sang BoardResponseDTO
    public BoardResponseDTO toDto(Board board) {
        return BoardResponseDTO.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .ownerName(board.getOwner().getUsername())
                .teamName(board.getTeam().getName())
                .createdBy(board.getCreatedBy().getUsername())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }
}
