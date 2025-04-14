package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardListResponseDTO {
    String id;
    String name;
    int position;
    //    BoardResponseDTO board;
    String boardId;
    List<TaskResponseDTO> tasks;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
