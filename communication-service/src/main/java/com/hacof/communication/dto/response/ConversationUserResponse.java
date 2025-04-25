package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

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
public class ConversationUserResponse {
    String id;
    String conversationId;
    String userId;
    String firstName;
    String lastName;
    String avatarUrl;
    boolean isDeleted;
    String deletedByUserName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
