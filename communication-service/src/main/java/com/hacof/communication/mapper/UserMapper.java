package com.hacof.communication.mapper;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.hacof.communication.dto.response.UserResponse;
import com.hacof.communication.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Named("mapRecipientToRecipients")
    default Set<UserResponse> mapRecipientToRecipients(User recipient) {
        return recipient != null ? Collections.singleton(toUserResponse(recipient)) : Collections.emptySet();
    }
}
