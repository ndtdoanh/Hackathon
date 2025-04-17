package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
