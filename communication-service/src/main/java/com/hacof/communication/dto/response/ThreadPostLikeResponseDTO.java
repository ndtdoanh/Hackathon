package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadPostLikeResponseDTO {

    String id;
    ThreadPostResponseDTO threadPost; // Include the full ThreadPost object
    String createdByUserName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
