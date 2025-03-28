package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardResponseDTO {

    String id;
    String name;
    String description;
    String ownerName;
    String teamName;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
