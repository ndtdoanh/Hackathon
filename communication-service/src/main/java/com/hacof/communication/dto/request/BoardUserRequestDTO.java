package com.hacof.communication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUserRequestDTO {
    private Long boardId;
    private Long userId;
    private String role; // ADMIN, MEMBER
}
