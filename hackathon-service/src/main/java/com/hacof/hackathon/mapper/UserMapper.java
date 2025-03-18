package com.hacof.hackathon.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserMapper {
    final UserRepository userRepository;

    @Named("mapUserFromId")
    public User mapUserFromId(Long userId) {
        return userId == null ? null : userRepository.findById(userId).orElse(null);
    }
}
