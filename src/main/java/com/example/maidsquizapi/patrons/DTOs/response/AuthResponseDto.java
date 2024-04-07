package com.example.maidsquizapi.patrons.DTOs.response;

import java.time.LocalDateTime;

public record AuthResponseDto(
        Integer id,
        String name,
        String phone,
        String email,
        String accessToken,
        LocalDateTime expiresOn
) {
}
