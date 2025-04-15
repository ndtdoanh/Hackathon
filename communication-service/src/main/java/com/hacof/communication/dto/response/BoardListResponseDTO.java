package com.hacof.communication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
