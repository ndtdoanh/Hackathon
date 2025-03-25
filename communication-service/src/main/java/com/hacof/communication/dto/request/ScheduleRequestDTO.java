package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {

    private Long teamId; // ID của Team liên kết với Schedule
    private String name; // Tên của Schedule
    private String description; // Mô tả về Schedule
}
