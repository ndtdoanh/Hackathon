package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;
import com.hacof.communication.dto.response.UserResponse;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardUser;
import com.hacof.communication.entity.User;

@Component
public class BoardUserMapper {

    public BoardUserResponseDTO toDto(BoardUser boardUser) {
        return BoardUserResponseDTO.builder()
                .id(String.valueOf(boardUser.getId()))
                .board(mapBoardToDto(boardUser.getBoard()))
                .user(mapUserToDto(boardUser.getUser()))
                .role(boardUser.getRole() != null ? boardUser.getRole().name() : null)
                .isDeleted(boardUser.isDeleted())
                .createdDate(boardUser.getCreatedDate())
                .lastModifiedDate(boardUser.getLastModifiedDate())
                .build();
    }

    private BoardResponseDTO mapBoardToDto(Board board) {
        return BoardResponseDTO.builder()
                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .description(board.getDescription())
                .ownerName(board.getOwner() != null ? board.getOwner().getUsername() : null)
                .teamId(String.valueOf(board.getTeam().getId()))
                .hackathonId(String.valueOf(board.getHackathon().getId())) // Lấy tên team (nếu có)
                .createdBy(board.getCreatedBy().getUsername())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }

    private UserResponse mapUserToDto(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
