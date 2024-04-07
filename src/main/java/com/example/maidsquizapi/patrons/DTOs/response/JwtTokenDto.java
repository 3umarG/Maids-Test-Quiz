package com.example.maidsquizapi.patrons.DTOs.response;

import java.util.Date;

public record JwtTokenDto(
        String token,
        Date expiresOn
) {
}
