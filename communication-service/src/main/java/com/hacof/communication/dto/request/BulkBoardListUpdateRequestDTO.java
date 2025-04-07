package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkBoardListUpdateRequestDTO {
    private String id;
    private String position;
}
