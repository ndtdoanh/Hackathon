package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.UserDTO;
import com.hacof.hackathon.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);
}
