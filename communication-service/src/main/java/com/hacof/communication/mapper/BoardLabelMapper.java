package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardLabel;
import org.springframework.stereotype.Component;

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
                .createdDate(boardLabel.getCreatedDate()) // Truyền thời gian tạo
                .lastModifiedDate(boardLabel.getLastModifiedDate()) // Truyền thời gian sửa đổi
                .build();
    }

    // Chuyển đổi Board sang BoardResponseDTO
    private BoardResponseDTO mapBoardToDto(Board board) {
        return BoardResponseDTO.builder()
                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .description(board.getDescription()) // Lấy mô tả Board
                .ownerName(board.getOwner() != null ? board.getOwner().getUsername() : null) // Lấy tên chủ sở hữu
                .teamId(String.valueOf(board.getTeam().getId()))
                .hackathonId(String.valueOf(board.getHackathon().getId())) // Lấy tên team (nếu có)
                .createdBy(board.getCreatedBy() != null ? board.getCreatedBy().getUsername() : null) // Người tạo
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }
}
