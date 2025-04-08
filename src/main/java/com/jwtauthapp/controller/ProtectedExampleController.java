package com.jwtauthapp.controller;

import com.jwtauthapp.service.JwtService;
import com.jwtauthapp.util.TimeUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class ProtectedExampleController {
    private final JwtService jwtService;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public ProtectedExampleController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @GetMapping("/details")
    public ResponseEntity<?>  getJwtDetails(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        Map<String, String> response = new HashMap<>();

        Claims claims = jwtService.extractAllClaims(token);

        System.out.println(claims);

        response.put("username", claims.getSubject());
        response.put("issuedAtHumanReadable", claims.getIssuedAt().toString());
        response.put("expiresAtHumanReadable", claims.getExpiration().toString());
        response.put("issuedAt", claims.get("iat").toString());
        response.put("expiresAt", claims.get("exp").toString());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        try {
            Claims claims = jwtService.extractAllClaims(token);

            String username = claims.getSubject();

            // (Optional) Check against DB or token store for blacklisted refresh tokens

            // Create a new access token
            String newAccessToken = generateAccessToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            response.put("expiresIn",String.valueOf(jwtService.getExpirationTime()));
            response.put("expiresInHumanReadable", TimeUtils.millisToHumanReadable(jwtService.getExpirationTime()));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token " +e.getMessage());
        }
    }

}