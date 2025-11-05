package com.kenji.web_order.service.Impl;

import com.kenji.web_order.dto.request.AuthenticationRequest;
import com.kenji.web_order.dto.request.RefreshTokenRequest;
import com.kenji.web_order.dto.request.TokenRequest;
import com.kenji.web_order.dto.response.AuthenticationResponse;
import com.kenji.web_order.dto.response.IntrospectResponse;
import com.kenji.web_order.entity.Token;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.repository.TokenRepository;
import com.kenji.web_order.repository.UserRepository;
import com.kenji.web_order.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    private static final int MAX_TOKENS = 3;

    @Override
    public IntrospectResponse introspect(TokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
            log.info("AppException: {}", e.getMessage());
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException, AppException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.MINUTES)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!verified || expiryTime.before(new Date())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ParseException, JOSEException {
        log.info("SignerKey: {}",SIGNER_KEY);
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        List<Token> tokens = tokenRepository.findAllByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.NO_USER_TOKEN));

        if(tokens.size()>=MAX_TOKENS){ //Kiểm tra số lượng token hợp lệ tối đa
            var deleteToken = tokens.get(0);
            tokenRepository.delete(deleteToken);
        }

        String token = generateToken(user);

        Token entityToken = getEntityTokenFromStringToken(token);

        tokenRepository.save(entityToken);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(entityToken.getRefreshToken())
                .authenticated(authenticated)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String jwtId = UUID.randomUUID().toString();
        var issueTime = Instant.now();
        var expirationTime = issueTime.plus(VALID_DURATION, ChronoUnit.MINUTES);
        String refreshToken = UUID.randomUUID().toString();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("kenji.com")
                .issueTime(new Date(issueTime.toEpochMilli()))
                .expirationTime(new Date (expirationTime.toEpochMilli()))
                .jwtID(jwtId)
                .claim("refreshToken", refreshToken)
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            String generatedToken = jwsObject.serialize();

            return generatedToken;

        } catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user.getRole()!=null) {
            stringJoiner.add("ROLE_" + user.getRole().getName());
        }
        return stringJoiner.toString();
    }

    @Override
    public void logout(TokenRequest request) throws ParseException, JOSEException {
        try {
            Token token = tokenRepository.findByToken(request.getToken()).orElseThrow(() ->
                    new AppException(ErrorCode.INVALID_KEY));
            token.setRevoked(true);
            tokenRepository.save(token);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {

        Token token = tokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (token.isRevoked()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var signedJWT = verifyToken(token.getToken(), true);
        var issueTime = signedJWT.getJWTClaimsSet().getIssueTime().toInstant();
        var refreshableTime = new Date(issueTime.plus(REFRESHABLE_DURATION, ChronoUnit.MINUTES).toEpochMilli());
        var user = token.getUser();

        if(refreshableTime.before(new Date())){
            throw  new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        String newToken = generateToken(user);
        Token entityToken = getEntityTokenFromStringToken(newToken);

        tokenRepository.delete(token);
        tokenRepository.save(entityToken);

        return AuthenticationResponse.builder()
                .token(newToken)
                .refreshToken(entityToken.getRefreshToken())
                .authenticated(true)
                .build();
    }

    private Token getEntityTokenFromStringToken(String token) throws ParseException, JOSEException {
        var signedJWT = verifyToken(token, false);
        String refreshToken = signedJWT.getJWTClaimsSet().getClaim("refreshToken").toString();
        String username = signedJWT.getJWTClaimsSet().getSubject().toString();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return Token.builder()
                .token(token)
                .refreshToken(refreshToken)
                .revoked(false)
                .user(user)
                .build();
    }
}
