package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import com.hacof.communication.constant.ReactionType;

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
public class MessageReactionResponse {
    Long id;
    ReactionType reactionType;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    Long createdByUserId;
}
