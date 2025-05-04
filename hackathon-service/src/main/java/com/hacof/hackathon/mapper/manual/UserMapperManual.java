package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.UserDTO;
import com.hacof.hackathon.entity.User;

public class UserMapperManual {
    public static UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(String.valueOf(user.getId()));
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setBio(user.getBio());

        return dto;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(Long.parseLong(userDTO.getId()));
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAvatarUrl(userDTO.getAvatarUrl());
        user.setBio(userDTO.getBio());
        user.setStatus(Status.valueOf(userDTO.getStatus()));
        return user;
    }
}
