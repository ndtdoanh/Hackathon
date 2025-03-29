package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.response.ConversationUserResponse;
import com.hacof.communication.entity.ConversationUser;

@Mapper(componentModel = "spring")
public interface ConversationUserMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "deletedByUserId", source = "deletedBy.id")
    ConversationUserResponse toConversationUserResponse(ConversationUser conversationUser);
}
