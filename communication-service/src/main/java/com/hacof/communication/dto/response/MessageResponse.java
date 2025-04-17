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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    String id;
    String conversationId;
    String content;
    boolean isDeleted;
    List<String> fileUrls;
    List<MessageReactionResponse> reactions;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
