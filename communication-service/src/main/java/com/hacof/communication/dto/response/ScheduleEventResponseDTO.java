package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.communication.constant.EventLabel;
import com.hacof.communication.entity.FileUrl;
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
public class ScheduleEventResponseDTO {

    String id;
//    ScheduleResponseDTO schedule;
    String scheduleId;
    String name;
    String description;
    String location;
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean isRecurring;
    String recurrenceRule;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    List<FileUrlResponse> fileUrls;
    EventLabel eventLabel;
}
