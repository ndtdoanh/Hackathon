package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.request.MessageReactionRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.entity.MessageReaction;

@Mapper(componentModel = "spring")
public interface MessageReactionMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(reaction.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(reaction.getCreatedBy() != null ? reaction.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    MessageReactionResponse toMessageReactionResponse(MessageReaction reaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "reactionType", source = "reactionType")
    MessageReaction toMessageReaction(MessageReactionRequest request);
}
