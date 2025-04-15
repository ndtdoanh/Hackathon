package com.hacof.communication.dto.request;

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
public class ScheduleEventRequestDTO {

    String scheduleId;
    String name;
    String description;
    String location;
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean isRecurring;
    String recurrenceRule;
    List<String> fileUrls;
}
