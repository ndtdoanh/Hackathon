package com.hacof.hackathon.service.impl;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    //    InvalidatedTokenRepository invalidatedTokenRepository;
    //
    //    @NonFinal
    //    @Value("${jwt.signerKey}")
    //    protected String signerKey;
    //
    //    @NonFinal
    //    @Value("${jwt.valid-duration}")
    //    protected long VALID_DURATION;
    //
    //    @NonFinal
    //    @Value("${jwt.refreshable-duration}")
    //    protected long REFRESHABLE_DURATION;
    //
    //    @Override
    //    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
    //        var token = request.getToken();
    //        boolean isValid = true;
    //
    //        try {
    //            verifyToken(token, false);
    //        } catch (AppException e) {
    //            isValid = false;
    //        }
    //
    //        return IntrospectResponse.builder().valid(isValid).build();
    //    }
    //
    //    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
    //        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
    //
    //        SignedJWT signedJWT = SignedJWT.parse(token);
    //
    //        Date expiryTime = (isRefresh)
    //                ? new Date(signedJWT
    //                        .getJWTClaimsSet()
    //                        .getIssueTime()
    //                        .toInstant()
    //                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
    //                        .toEpochMilli())
    //                : signedJWT.getJWTClaimsSet().getExpirationTime();
    //
    //        var verified = signedJWT.verify(verifier);
    //
    //        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);
    //
    //        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
    //            throw new AppException(ErrorCode.UNAUTHENTICATED);
    //
    //        return signedJWT;
    //    }

    //    private String buildScope(User user) {
    //        StringJoiner stringJoiner = new StringJoiner(" ");
    //
    //        if (!CollectionUtils.isEmpty(user.getRoles()))
    //            user.getRoles().forEach(role -> {
    //                stringJoiner.add(role.getName());
    //
    //                if (!CollectionUtils.isEmpty(role.getPermissions()))
    //                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
    //            });
    //
    //        return stringJoiner.toString();
    //    }
}
