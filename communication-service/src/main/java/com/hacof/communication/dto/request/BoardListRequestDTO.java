package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListRequestDTO {
    private String name;
    private int position;
    private Long boardId;
}
