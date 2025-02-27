package com.hacof.identity.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hacof.identity.dtos.request.PasswordCreateRequest;
import com.hacof.identity.dtos.request.UserCreateRequest;
import com.hacof.identity.dtos.request.UserUpdateRequest;
import com.hacof.identity.dtos.response.RoleResponse;
import com.hacof.identity.dtos.response.UserResponse;
import com.hacof.identity.entities.Role;
import com.hacof.identity.entities.User;
import com.hacof.identity.enums.Status;
import com.hacof.identity.exceptions.AppException;
import com.hacof.identity.exceptions.ErrorCode;
import com.hacof.identity.mappers.UserMapper;
import com.hacof.identity.repositories.RoleRepository;
import com.hacof.identity.repositories.UserRepository;
import com.hacof.identity.services.RoleService;
import com.hacof.identity.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    RoleService roleService;

    @Override
    public UserResponse createUser(String token, UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        RoleResponse creatorRole = roleService.getRoleFromToken(token);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = userMapper.toUser(request);

        user.setPassword(encodedPassword);
        user.setIsVerified(false);
        user.setStatus(Status.ACTIVE);

        if (creatorRole.getName().equals("ADMIN")) {
            if (request.getAssignedRole() != null && !request.getAssignedRole().equals("ORGANIZATION")) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            Role organizationRole = roleRepository
                    .findByName("ORGANIZATION")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            user.setRoles(Set.of(organizationRole));

        } else if (creatorRole.getName().equals("ORGANIZATION")) {
            if (request.getAssignedRole() == null || request.getAssignedRole().isBlank()) {
                throw new AppException(ErrorCode.ASSIGNED_ROLE_IS_REQUIRED);
            }

            if (!request.getAssignedRole().equals("JUDGE")
                    && !request.getAssignedRole().equals("MENTOR")) {
                throw new AppException(ErrorCode.INVALID_ASSIGNED_ROLE);
            }

            Role assignedRole = roleRepository
                    .findByName(request.getAssignedRole())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            user.setRoles(Set.of(assignedRole));

        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void createPassword(PasswordCreateRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (StringUtils.hasText(user.getPassword())) throw new AppException(ErrorCode.PASSWORD_EXISTED);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var userResponse = userMapper.toUserResponse(user);
        userResponse.setNoPassword(!StringUtils.hasText(user.getPassword()));

        return userResponse;
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUser(Long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!Objects.equals(currentUser.getId(), userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roleIds = request.getRoles().stream().map(Long::valueOf).collect(Collectors.toList());

        var roles = roleRepository.findAllById(roleIds);
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
