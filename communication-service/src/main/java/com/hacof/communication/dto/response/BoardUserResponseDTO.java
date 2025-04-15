package com.hacof.communication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUserResponseDTO {
    private String id;
    private BoardResponseDTO board;
    private UserResponse user;
    private String role; // ADMIN, MEMBER
    private boolean isDeleted;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
