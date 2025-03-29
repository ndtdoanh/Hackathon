package com.hacof.communication.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleRequestDTO {

    String teamId; // ID của Team liên kết với Schedule
    String name; // Tên của Schedule
    String description; // Mô tả về Schedule
}
