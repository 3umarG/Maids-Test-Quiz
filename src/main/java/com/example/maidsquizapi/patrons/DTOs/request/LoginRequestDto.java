package com.example.maidsquizapi.patrons.DTOs.request;

import jakarta.validation.constraints.*;

public record LoginRequestDto(
        @Email(
                message = "Invalid email format. Please provide a valid email address."
        )
        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        @Size(
                min = 8,
                message = "password must be at least 8 chars"
        )
        String password
) {
}
