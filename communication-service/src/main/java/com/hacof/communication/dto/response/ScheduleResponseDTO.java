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
