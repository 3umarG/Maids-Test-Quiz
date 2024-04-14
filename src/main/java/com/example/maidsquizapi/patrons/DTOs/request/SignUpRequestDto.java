package com.example.maidsquizapi.patrons.DTOs.request;

import com.example.maidsquizapi.patrons.enums.UserRole;
import jakarta.validation.constraints.*;

public record SignUpRequestDto(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^01[0125][0-9]{8}$", message = "Please enter valid Egyptian phone number format.")
        String phone,


        @Email(message = "Invalid email format. Please provide a valid email address.")
        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        @Size(
                min = 8,
                message = "password must be at least 8 chars"
        )
        String password,

        UserRole role
) {
}
