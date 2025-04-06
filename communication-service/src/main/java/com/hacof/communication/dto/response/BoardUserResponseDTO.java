package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;

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
