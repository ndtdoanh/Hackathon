package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDTO {

    private String name;
    private String description;
    private Long ownerId;
    private Long teamId;
}
