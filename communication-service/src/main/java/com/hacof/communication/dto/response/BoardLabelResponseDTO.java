package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLabelResponseDTO {

    private Long id;
    private String name;
    private String color;
    private BoardResponseDTO board;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
