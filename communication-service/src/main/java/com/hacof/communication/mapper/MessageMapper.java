package com.hacof.communication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.communication.dto.request.MessageCreateRequest;
import com.hacof.communication.dto.request.MessageUpdateRequest;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(message.getId()))")
    @Mapping(
            target = "createdByUserId",
            expression = "java(message.getCreatedBy() != null ? String.valueOf(message.getCreatedBy().getId()) : null)")
    @Mapping(
            target = "conversationId",
            expression =
                    "java(message.getConversation() != null ? String.valueOf(message.getConversation().getId()) : null)")
    MessageResponse toMessageResponse(Message message);

    @Mapping(target = "id", ignore = true)
    Message toMessage(MessageCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateMessageFromRequest(MessageUpdateRequest request, @MappingTarget Message message);
}
