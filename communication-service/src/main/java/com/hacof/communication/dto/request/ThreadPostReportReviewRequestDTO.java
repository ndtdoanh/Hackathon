package com.hacof.communication.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadPostReportReviewRequestDTO {
    String status; // REVIEWED or DISMISSED
}
