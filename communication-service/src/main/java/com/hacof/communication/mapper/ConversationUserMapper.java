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
            target = "deletedByUserName",
            expression =
                    "java(conversationUser.getDeletedBy() != null ? conversationUser.getDeletedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    ConversationUserResponse toConversationUserResponse(ConversationUser conversationUser);
}
