package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String ownerName;
    private String teamName;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
