package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    String id;
    String conversationId;
    String content;
    List<FileUrlResponse> fileUrls;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
