package com.example.maidsquizapi.patrons.DTOs.request;

import jakarta.validation.constraints.*;

public record UpdatePatronRequestDto(

        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^01[0125][0-9]{8}$")
        String phone,


        @Email(message = "Invalid email format. Please provide a valid email address.")
        @NotBlank
        @NotNull
        String email
) {
}
