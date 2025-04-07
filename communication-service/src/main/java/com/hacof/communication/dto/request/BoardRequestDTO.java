package com.hacof.communication.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardRequestDTO {

    String name;
    String description;
    String ownerId;
    String teamId;
    String hackathonId;
}
