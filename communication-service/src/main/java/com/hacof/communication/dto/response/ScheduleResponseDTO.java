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
public class ScheduleResponseDTO {

    String id;
    String teamId;
    String hackathonId;
    String name;
    String description;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdBy;
    List<ScheduleEventResponseDTO> scheduleEvents;
}
