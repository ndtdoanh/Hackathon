package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponseDTO {
    private Long id;
    private String name;
    private int position;
    private BoardResponseDTO board;
    private List<TaskResponseDTO> tasks;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
