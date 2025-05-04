package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardLabel;

@Component
public class BoardLabelMapper {

    // Chuyển từ BoardLabelRequestDTO sang BoardLabel entity
    public BoardLabel toEntity(BoardLabelRequestDTO requestDTO, Board board) {
        return BoardLabel.builder()
                .name(requestDTO.getName())
                .color(requestDTO.getColor())
                .board(board)
                .build();
    }

    // Chuyển từ BoardLabel entity sang BoardLabelResponseDTO
    public BoardLabelResponseDTO toDto(BoardLabel boardLabel) {
        return BoardLabelResponseDTO.builder()
                .id(String.valueOf(boardLabel.getId()))
                .name(boardLabel.getName())
                .color(boardLabel.getColor())
                .board(boardLabel.getBoard() != null ? mapBoardToDto(boardLabel.getBoard()) : null) // Map toàn bộ Board
                .createdAt(boardLabel.getCreatedDate()) // Truyền thời gian tạo
                .updatedAt(boardLabel.getLastModifiedDate()) // Truyền thời gian sửa đổi
                .build();
    }

    // Chuyển đổi Board sang BoardResponseDTO
    private BoardResponseDTO mapBoardToDto(Board board) {
        return BoardResponseDTO.builder()
                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .description(board.getDescription())
                .ownerId(
                        board.getOwner() != null
                                ? String.valueOf(board.getOwner().getId())
                                : null)
                .teamId(board.getTeam() != null ? String.valueOf(board.getTeam().getId()) : null)
                .hackathonId(
                        board.getHackathon() != null
                                ? String.valueOf(board.getHackathon().getId())
                                : null)
                .createdBy(board.getCreatedBy() != null ? board.getCreatedBy().getUsername() : null)
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }
}
