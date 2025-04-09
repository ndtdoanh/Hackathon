package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.Hackathon;
import com.hacof.communication.entity.Team;
import com.hacof.communication.entity.User;

@Component
public class BoardMapper {
    // Convert from BoardRequestDTO to Board entity
    public Board toEntity(BoardRequestDTO boardRequestDTO, User owner, Team team, Hackathon hackathon) {
        return Board.builder()
                .name(boardRequestDTO.getName())
                .description(boardRequestDTO.getDescription())
                .owner(owner)
                .team(team)
                .hackathon(hackathon) // Mapping hackathon
                .build();
    }

    // Convert from Board entity to BoardResponseDTO
    public BoardResponseDTO toDto(Board board) {
        return BoardResponseDTO.builder()
                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .description(board.getDescription())
                .ownerName(board.getOwner().getUsername())
                .teamId(String.valueOf(board.getTeam().getId()))
                .hackathonId(
                        board.getHackathon() != null
                                ? String.valueOf(board.getHackathon().getId())
                                : null) // Handle hackathonId
                .createdBy(board.getCreatedBy().getUsername())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }
}
