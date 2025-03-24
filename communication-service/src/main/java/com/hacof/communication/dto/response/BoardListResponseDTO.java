package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

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
    private String boardName;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
