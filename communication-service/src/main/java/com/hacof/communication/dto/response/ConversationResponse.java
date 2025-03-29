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
    Long id;
    String name;
    ConversationType type;
    Long teamId;
    Set<ConversationUserResponse> users;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    Long createdByUserId;
}
