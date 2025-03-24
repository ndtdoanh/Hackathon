package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLabelRequestDTO {

    private String name;
    private String color;
    private Long boardId;
}
