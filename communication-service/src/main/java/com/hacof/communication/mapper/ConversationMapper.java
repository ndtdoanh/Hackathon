package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.dto.response.ConversationUserResponse;
import com.hacof.communication.entity.Conversation;
import com.hacof.communication.entity.ConversationUser;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "createdByUserId", source = "createdBy.id")
    @Mapping(target = "users", source = "conversationUsers")
    ConversationResponse toConversationResponse(Conversation conversation);
}
