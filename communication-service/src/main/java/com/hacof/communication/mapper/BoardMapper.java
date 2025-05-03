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
                .ownerId(board.getOwner() != null ? String.valueOf(board.getOwner().getId()) : null)
                .teamId(board.getTeam() != null ? String.valueOf(board.getTeam().getId()) : null)
                .hackathonId(board.getHackathon() != null ? String.valueOf(board.getHackathon().getId()) : null)
                .createdBy(board.getCreatedBy() != null ? board.getCreatedBy().getUsername() : null)
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }
}
