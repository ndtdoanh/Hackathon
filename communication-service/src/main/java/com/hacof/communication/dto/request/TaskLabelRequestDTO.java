package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLabelRequestDTO {

    private Long taskId;
    private Long boardLabelId;
}
