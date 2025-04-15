package com.hacof.identity.service.impl;

import com.hacof.identity.constant.ProviderName;
import com.hacof.identity.constant.RoleType;
import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.AuthenticationRequest;
import com.hacof.identity.dto.request.ExchangeTokenRequest;
import com.hacof.identity.dto.request.IntrospectRequest;
import com.hacof.identity.dto.request.LogoutRequest;
import com.hacof.identity.dto.request.RefreshRequest;
import com.hacof.identity.dto.response.AuthenticationResponse;
import com.hacof.identity.dto.response.IntrospectResponse;
import com.hacof.identity.entity.InvalidatedToken;
import com.hacof.identity.entity.Role;
import com.hacof.identity.entity.ThirdpartyAuthprovider;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserRole;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.repository.InvalidatedTokenRepository;
import com.hacof.identity.repository.RoleRepository;
import com.hacof.identity.repository.ThirdpartyAuthproviderRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.repository.httpclient.OutboundIdentityClient;
import com.hacof.identity.repository.httpclient.OutboundUserClient;
import com.hacof.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    OutboundIdentityClient outboundIdentityClient;
    OutboundUserClient outboundUserClient;
    RoleRepository roleRepository;
    ThirdpartyAuthproviderRepository thirdpartyAuthproviderRepository;
    StringRedisTemplate redisTemplate;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public AuthenticationResponse outboundAuthenticate(String code) {
        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build());

        log.info("TOKEN RESPONSE {}", response);

        var userInfo = outboundUserClient.getUserInfo("json", response.getAccessToken());
        log.info("User Info {}", userInfo);

        User user = userRepository.findByEmail(userInfo.getEmail()).orElse(null);

        if (user == null) {
            Role teamMemberRole = roleRepository
                    .findByName(RoleType.TEAM_MEMBER.name())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

            user = User.builder()
                    .username(userInfo.getEmail())
                    .email(userInfo.getEmail())
                    .firstName(userInfo.getGivenName())
                    .lastName(userInfo.getFamilyName())
                    .isVerified(userInfo.isVerifiedEmail())
                    .status(Status.ACTIVE)
                    .build();

            UserRole userRole =
                    UserRole.builder().user(user).role(teamMemberRole).build();

            user.getUserRoles().add(userRole);

            user = userRepository.save(user);
            log.info("User created with email: {}", user.getUsername());

        } else {
            user.setFirstName(userInfo.getGivenName());
            user.setLastName(userInfo.getFamilyName());
            user.setVerified(userInfo.isVerifiedEmail());

            user.setLastModifiedBy(user);
            userRepository.save(user);
            log.info("User updated with email: {}", user.getUsername());
        }

        boolean exists = thirdpartyAuthproviderRepository.existsByUserAndProviderName(user, ProviderName.GOOGLE);
        if (!exists) {
            ThirdpartyAuthprovider authProvider = ThirdpartyAuthprovider.builder()
                    .user(user)
                    .providerName(ProviderName.GOOGLE)
                    .providerUserId(userInfo.getId())
                    .build();
            thirdpartyAuthproviderRepository.save(authProvider);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.INVALID_CREDENTIALS);

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    //    @Override
    //    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    //
    //        String username = request.getUsername();
    //        String failKey = "login:fail:" + username;
    //        String lockKey = "login:lock:" + username;
    //
    //        String lockUntilStr = redisTemplate.opsForValue().get(lockKey);
    //        if (lockUntilStr != null) {
    //            long lockUntil = Long.parseLong(lockUntilStr);
    //            long now = System.currentTimeMillis();
    //            if (now < lockUntil) {
    //                long secondsLeft = (lockUntil - now) / 1000;
    //
    //                throw new AppException(ErrorCode.ACCOUNT_LOCKED,
    //                        "You have entered the wrong many times, please try again later " + secondsLeft + "
    // seconds.");
    //            } else {
    //                redisTemplate.delete(lockKey);
    //            }
    //        }
    //
    //        var user = userRepository
    //                .findByUsername(request.getUsername())
    //                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));
    //
    //        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    //        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
    //
    //        if (!authenticated) {
    //            Long failCount = redisTemplate.opsForValue().increment(failKey);
    //            redisTemplate.expire(failKey, Duration.ofMinutes(5));
    //
    //            if (failCount == 3) {
    //                long lockUntil = System.currentTimeMillis() + 30_000;
    //                redisTemplate.opsForValue().set(lockKey, String.valueOf(lockUntil), Duration.ofSeconds(30));
    //            } else if (failCount == 5) {
    //                long lockUntil = System.currentTimeMillis() + 60_000;
    //                redisTemplate.opsForValue().set(lockKey, String.valueOf(lockUntil), Duration.ofSeconds(60));
    //            } else if (failCount == 7) {
    //                long lockUntil = System.currentTimeMillis() + 60 * 60_000L;
    //                redisTemplate.opsForValue().set(lockKey, String.valueOf(lockUntil), Duration.ofHours(1));
    //            } else if (failCount == 10) {
    //                long lockUntil = System.currentTimeMillis() + 24 * 60 * 60_000L;
    //                redisTemplate.opsForValue().set(lockKey, String.valueOf(lockUntil), Duration.ofDays(1));
    //            } else {
    //            }
    //
    //            throw new AppException(ErrorCode.INVALID_CREDENTIALS, "False password " + failCount + " times");
    //        }
    //
    //        redisTemplate.delete(failKey);
    //        redisTemplate.delete(lockKey);
    //
    //        var token = generateToken(user);
    //        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    //    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var email = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(email).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Set<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());

        Set<String> permissions = user.getUserRoles().stream()
                .flatMap(userRole -> userRole.getRole().getRolePermissions().stream())
                .map(rolePermission -> rolePermission.getPermission().getName())
                .collect(Collectors.toSet());

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ndtdoanh.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("user_id", user.getId())
                .claim("role", roles)
                .claim("permissions", permissions)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create toklen", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getUserRoles()))
            user.getUserRoles().forEach(userRole -> {
                stringJoiner.add(userRole.getRole().getName());

                if (!CollectionUtils.isEmpty(userRole.getRole().getRolePermissions()))
                    userRole.getRole()
                            .getRolePermissions()
                            .forEach(rolePermission -> stringJoiner.add(
                                    rolePermission.getPermission().getName()));
            });

        return stringJoiner.toString();
    }
}
