package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.response.ConversationUserResponse;
import com.hacof.communication.entity.ConversationUser;

@Mapper(componentModel = "spring")
public interface ConversationUserMapper {
    @Mapping(
            target = "userId",
            expression =
                    "java(conversationUser.getUser() != null ? String.valueOf(conversationUser.getUser().getId()) : null)")
    @Mapping(
            target = "deletedByUserId",
            expression =
                    "java(conversationUser.getDeletedBy() != null ? String.valueOf(conversationUser.getDeletedBy().getId()) : null)")
    ConversationUserResponse toConversationUserResponse(ConversationUser conversationUser);
}
