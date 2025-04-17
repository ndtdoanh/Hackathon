package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.hacof.communication.constant.ConversationType;

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
public class ConversationResponse {
    String id;
    ConversationType type;
    String name;
    String avatarUrl;
    Set<ConversationUserResponse> conversationUsers;
    List<MessageResponse> messages;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
