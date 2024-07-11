package com.enigmacamp.tokonyadia.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.tokonyadia.model.entities.ActiveUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${app.tokonyadiashop.jwt.jwt-secret}")
    private String jwtSecret;
    @Value("${app.tokonyadiashop.jwt.app-name}")
    private String appName;
    @Value("${app.tokonyadiashop.jwt.jwt-expired}")
    private long jwtExpired;

    private Algorithm algorithm;

    @PostConstruct
    public void initAlgorithm() {
        this.algorithm = Algorithm.HMAC256(jwtSecret);
    }

    public String generateToken(ActiveUser activeUser) {
        return JWT.create().withIssuer(appName)
                .withSubject(activeUser.getId())
                .withExpiresAt(Instant.now().plusSeconds(jwtExpired))
                .withIssuedAt(Instant.now())
                .withClaim("role", activeUser.getRole().name())
                .sign(algorithm);
    }

    public boolean verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getIssuer().equals(appName);
    }

    public Map<String, String> getUserInfoByToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", decodedJWT.getSubject());
        userInfo.put("role", decodedJWT.getClaim("role").asString());
        return userInfo;
    }
}