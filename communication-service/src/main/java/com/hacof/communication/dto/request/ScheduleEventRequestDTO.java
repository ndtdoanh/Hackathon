package com.hacof.communication.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.communication.entity.FileUrl;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
