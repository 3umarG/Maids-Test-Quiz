package com.example.maidsquizapi.patrons.DTOs.response;

public record PatronResponseDto(
        Integer id,
        String name,
        String phone,
        String email
) {
}
