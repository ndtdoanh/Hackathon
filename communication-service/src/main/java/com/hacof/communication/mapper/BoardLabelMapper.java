package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
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
                .id(boardLabel.getId())
                .name(boardLabel.getName())
                .color(boardLabel.getColor())
                .boardName(boardLabel.getBoard().getName()) // Giả sử Board có trường name
                .createdDate(boardLabel.getCreatedDate()) // Truyền thời gian tạo
                .lastModifiedDate(boardLabel.getLastModifiedDate()) // Truyền thời gian sửa đổi
                .build();
    }
}
