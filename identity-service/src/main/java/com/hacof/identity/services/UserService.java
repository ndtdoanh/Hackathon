package com.hacof.identity.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hacof.identity.configs.SecurityConfig;
import com.hacof.identity.dtos.request.UserUpdateRequest;
import com.hacof.identity.dtos.response.UserResponse;
import com.hacof.identity.entities.User;
import com.hacof.identity.exceptions.AppException;
import com.hacof.identity.exceptions.ErrorCode;
import com.hacof.identity.mappers.UserMapper;
import com.hacof.identity.repositories.RoleRepository;
import com.hacof.identity.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    SecurityConfig securityConfig;
    RoleRepository roleRepository;

    //    public UserResponse createUser(UserCreateRequest request, String jwtToken) {
    //        if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.USER_EXISTED);
    //
    //        String creatorRole = securityConfig.getRoleFromToken(jwtToken);
    //        log.info("Role from token: {}", creatorRole);
    //        Set<Role> roles = new HashSet<>();
    //
    //        if (creatorRole.equals(RoleType.ADMIN.name())) {
    //            roles.add(getRole(RoleType.ORGANIZATION));
    //        } else if (creatorRole.equals(RoleType.ORGANIZATION.name())) {
    //            if (request.getAssignedRole() != null
    //                    && (request.getAssignedRole().equals(RoleType.MENTOR.name())
    //                            || request.getAssignedRole().equals(RoleType.JUDGE.name()))) {
    //                roles.add(getRole(RoleType.valueOf(request.getAssignedRole())));
    //            } else {
    //                throw new AppException(ErrorCode.INVALID_ASSIGNED_ROLE);
    //            }
    //        } else {
    //            throw new AppException(ErrorCode.UNAUTHORIZED);
    //        }
    //
    //        User user = userMapper.toUser(request);
    //        user.setPassword(passwordEncoder.encode(request.getPassword()));
    //        user.setRoles(roles);
    //
    //        User savedUser = userRepository.save(user);
    //
    //        return userMapper.toUserResponse(savedUser);
    //    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(Long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roleIds = request.getRoles().stream().map(Long::valueOf).collect(Collectors.toList());

        var roles = roleRepository.findAllById(roleIds);
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
