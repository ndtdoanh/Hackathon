package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUserResponseDTO {
    private String id;
//    private BoardResponseDTO board;
//    private UserResponse user;
    private String boardId;
    private String userId;
    private String role; // ADMIN, MEMBER
    private boolean isDeleted;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
