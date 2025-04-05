package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

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
public class ConversationUserResponse {
    String id;
    String conversationId;
    String userId;
    String firstName;
    String lastName;
    boolean isDeleted;
    String deletedByUserName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
