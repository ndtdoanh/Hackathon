package com.hacof.communication.dto.response;

import com.hacof.communication.constant.ReactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageReactionResponse {
    String id;
    String messageId;
    ReactionType reactionType;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
