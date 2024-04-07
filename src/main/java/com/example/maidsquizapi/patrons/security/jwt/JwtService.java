package com.example.maidsquizapi.patrons.security.jwt;

import com.example.maidsquizapi.patrons.DTOs.response.JwtTokenDto;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUserName(String token);

    JwtTokenDto generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    JwtTokenDto generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolvers);

    Date extractExpiration(String token);
}
