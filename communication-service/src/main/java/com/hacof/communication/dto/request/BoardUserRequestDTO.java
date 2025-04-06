package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUserRequestDTO {
    private Long boardId;
    private Long userId;
    private String role; // ADMIN, MEMBER
}
