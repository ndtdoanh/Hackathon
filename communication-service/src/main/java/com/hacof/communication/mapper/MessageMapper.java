package com.hacof.communication.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(source = "createdBy.id", target = "createdBy")
    MessageResponse messageToResponse(Message message);

    List<MessageResponse> messagesToResponses(List<Message> messages);
}
