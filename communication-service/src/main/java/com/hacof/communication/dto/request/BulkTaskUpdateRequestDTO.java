package com.hacof.communication.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BulkTaskUpdateRequestDTO {
    private String id; // Task ID
    private String boardListId; // BoardList ID
    private int position; // Position
}
