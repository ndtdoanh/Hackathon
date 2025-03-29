package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.entity.Conversation;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(
            target = "teamId",
            expression = "java(conversation.getTeam() != null ? String.valueOf(conversation.getTeam().getId()) : null)")
    @Mapping(target = "id", expression = "java(String.valueOf(conversation.getId()))")
    @Mapping(
            target = "createdByUserId",
            expression =
                    "java(conversation.getCreatedBy() != null ? String.valueOf(conversation.getCreatedBy().getId()) : null)")
    @Mapping(target = "users", source = "conversationUsers")
    ConversationResponse toConversationResponse(Conversation conversation);
}
