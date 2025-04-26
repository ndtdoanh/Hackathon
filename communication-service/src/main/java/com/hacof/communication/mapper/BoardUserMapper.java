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
                //                .boardId(String.valueOf(boardUser.getBoard().getId()))
                //                .userId(String.valueOf(boardUser.getUser().getId()))
                .role(boardUser.getRole() != null ? boardUser.getRole().name() : null)
                .isDeleted(boardUser.isDeleted())
                .deletedById(
                        boardUser.getDeletedBy() != null
                                ? String.valueOf(boardUser.getDeletedBy().getId())
                                : null)
                .createdAt(boardUser.getCreatedDate())
                .updatedAt(boardUser.getLastModifiedDate())
                .build();
    }

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
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
