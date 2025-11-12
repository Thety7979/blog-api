package com.tytran.blog.services.implement;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.tytran.blog.dto.request.AuthRequestDTO;
import com.tytran.blog.dto.request.IntrospectRequestDTO;
import com.tytran.blog.dto.request.LogoutRequestDTO;
import com.tytran.blog.dto.request.RefreshTokenRequestDTO;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.AuthResponseDTO;
import com.tytran.blog.dto.response.IntrospectResponseDTO;
import com.tytran.blog.dto.response.RefreshTokenResponseDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.InvalidatedToken;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.UserMapper;
import com.tytran.blog.repository.InvalidatedTokenRepository;
import com.tytran.blog.repository.UserRepository;
import com.tytran.blog.services.AuthService;
import com.tytran.blog.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthServiceImpl implements AuthService {

    InvalidatedTokenRepository invalidatedTokenRepository;

    UserService userService;

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    @Override
    public UserDTO Register(RegisterRequestDTO request) {
        return userService.saveUser(request);
    }

    @Override
    public IntrospectResponseDTO introspect(IntrospectRequestDTO request) throws JOSEException, ParseException {
        Boolean isvalid = true;
        try {
            verifyToken(request.getToken(), false);
        } catch (Exception e) {
            isvalid = false;
        }
        return IntrospectResponseDTO.builder()
                .valid(isvalid)
                .build();
    }

    @Override
    public AuthResponseDTO Login(AuthRequestDTO request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_OR_PASSWORD_NOT_TRUE));
        Boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new AppException(ErrorCode.EMAIL_OR_PASSWORD_NOT_TRUE);
        }
        return AuthResponseDTO.builder()
                .token(generateToken(user))
                .user(userMapper.userToUserDTO(user))
                .build();
    }

    @Override
    public Boolean Logout(LogoutRequestDTO requestDTO) throws JOSEException, ParseException {
        try {
            var signToken = verifyToken(requestDTO.getToken(), true);
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(signToken.getJWTClaimsSet().getJWTID())
                    .expiryTime(signToken.getJWTClaimsSet().getExpirationTime())
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expiry");
        }
        return true;
    }

    private SignedJWT verifyToken(String token, Boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if (!verified || expiryTime.before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Boolean checkExists = invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());
        if (checkExists) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    private String generateToken(Users user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("tytran.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Users user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!role.getPermission().isEmpty()) {
                    role.getPermission().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO request) throws JOSEException, ParseException {
        var signedToken = verifyToken(request.getToken(), true);
        var jwtId = signedToken.getJWTClaimsSet().getJWTID();
        var expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwtId)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        String email = signedToken.getJWTClaimsSet().getSubject();
        Users user = userService.findByEmail(email);
        String refreshToken = generateToken(user);
        return RefreshTokenResponseDTO.builder()
                .Token(refreshToken)
                .build();
    }

}
