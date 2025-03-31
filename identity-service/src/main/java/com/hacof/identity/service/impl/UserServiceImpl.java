package com.hacof.identity.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.ChangePasswordRequest;
import com.hacof.identity.dto.request.ForgotPasswordRequest;
import com.hacof.identity.dto.request.PasswordCreateRequest;
import com.hacof.identity.dto.request.ResetPasswordRequest;
import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.response.AvatarResponse;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.entity.Role;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserRole;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.UserMapper;
import com.hacof.identity.repository.RoleRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.repository.UserRoleRepository;
import com.hacof.identity.service.EmailService;
import com.hacof.identity.service.OtpService;
import com.hacof.identity.service.RoleService;
import com.hacof.identity.service.UserService;
import com.hacof.identity.util.AuditContext;

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
    EmailService emailService;
    OtpService otpService;
    S3Service s3Service;
    UserRoleRepository userRoleRepository;

    @Override
    public UserResponse createUser(String token, UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        RoleResponse creatorRole = roleService.getRoleFromToken(token);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = userMapper.toUser(request);
        user.setPassword(encodedPassword);
        user.setVerified(false);
        user.setStatus(Status.ACTIVE);

        if (request.getUserRoles() == null || request.getUserRoles().getRoleId().isBlank()) {
            throw new AppException(ErrorCode.ROLE_ID_IS_REQUIRED);
        }

        Role assignedRole = roleRepository
                .findById(Long.valueOf(request.getUserRoles().getRoleId()))
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if ("ADMIN".equals(creatorRole.getName())) {
            if (!"ORGANIZATION".equals(assignedRole.getName())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        } else if ("ORGANIZATION".equals(creatorRole.getName())) {
            if (!"JUDGE".equals(assignedRole.getName()) && !"MENTOR".equals(assignedRole.getName())) {
                throw new AppException(ErrorCode.INVALID_ASSIGNED_ROLE);
            }
        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(assignedRole);
        user.getUserRoles().add(userRole);

        user = userRepository.save(user);
        user = userRepository.findById(user.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

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
    public UserResponse getUserById(Long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse getUserByUserName(String username) {
        return userMapper.toUserResponse(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public List<UserResponse> getUsersByRoles() {
        List<String> allowedRoles = Arrays.asList("ORGANIZATION", "JUDGE", "MENTOR");
        return userRepository.findAllByRoles(allowedRoles).stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getTeamMembers() {
        return userRepository.findTeamMembers("TEAM_MEMBER").stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse updateMyInfo(UserUpdateRequest request) {
        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository
                .findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        if (request.getSkills() != null && !request.getSkills().isEmpty()) {
            user.setSkills(request.getSkills());
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public String addEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email has been used by another account");
        }

        User user = AuditContext.getCurrentUser();
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        user.setTempEmail(email);
        userRepository.save(user);

        String otp = otpService.generateOtp(email);
        emailService.sendOtp(email, otp);

        return "OTP code has been sent to email " + email + ". Please check and verify your email.";
    }

    @Override
    public String verifyEmail(Long userId, String otp) {

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String tempEmail = user.getTempEmail();

        if (tempEmail == null || tempEmail.isEmpty()) {
            throw new IllegalStateException("You have not registered email to verify");
        }

        if (!otpService.verifyOtp(tempEmail, otp)) {
            throw new IllegalArgumentException("OTP code is incorrect or expired");
        }

        user.setEmail(tempEmail);
        user.setTempEmail(null);
        user.setVerified(true);
        userRepository.save(user);

        return "Email " + tempEmail + " has been verified successfully";
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }

        User user = AuditContext.getCurrentUser();
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.NEW_PASSWORD_SAME_AS_OLD);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Change the password successfully";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {

        String email = request.getEmail();

        if (!isValidEmail(email)) {
            throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getIsVerified()) {
            throw new AppException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        String otp = otpService.generateOtp("reset_password_" + email);
        emailService.sendPasswordResetOtp(email, otp);

        return "OTP code reset password has been sent to email " + email + ". Please check your email.";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_MISMATCH);
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!otpService.verifyOtp("reset_password_" + request.getEmail(), request.getOtp())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        otpService.removeOtp("reset_password_" + request.getEmail());

        return "Reset password successfully";
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @Override
    public AvatarResponse uploadAvatar(MultipartFile file, Authentication authentication) throws IOException {

        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = optionalUser.get();
        String fileUrl = s3Service.uploadFile(
                file.getInputStream(), file.getOriginalFilename(), file.getSize(), file.getContentType());

        user.setAvatarUrl(fileUrl);
        LocalDateTime uploadedAt = LocalDateTime.now();
        user.setUploadedAt(uploadedAt);
        userRepository.save(user);

        return new AvatarResponse(String.valueOf(user.getId()), fileUrl, uploadedAt);
    }

    private static final List<String> ALLOWED_FILE_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/bmp",
            "image/tiff",
            "image/webp",
            "image/svg+xml",
            "image/x-icon");
}
