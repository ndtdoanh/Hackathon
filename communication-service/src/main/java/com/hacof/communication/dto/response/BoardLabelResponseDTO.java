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
public class BoardLabelResponseDTO {

    String id;
    String name;
    String color;
    BoardResponseDTO board;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
