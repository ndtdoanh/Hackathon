package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.hacof.communication.constant.ConversationType;

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
public class ConversationResponse {
    String id;
    String name;
    ConversationType type;
    String teamId;
    Set<ConversationUserResponse> users;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdByUserId;
}
