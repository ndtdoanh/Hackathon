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
    @Mapping(target = "createdByUserId", source = "createdBy.id")
    @Mapping(target = "conversationId", source = "conversation.id")
    MessageResponse toMessageResponse(Message message);

    @Mapping(target = "id", ignore = true)
    Message toMessage(MessageCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateMessageFromRequest(MessageUpdateRequest request, @MappingTarget Message message);
}
