package com.example.maidsquizapi.borrowing.DTOs;

import java.time.LocalDate;

public record BorrowedBookResponseDto(
        Integer patronId,
        String patronName,
        Integer bookId,
        String bookTitle,
        LocalDate borrowedOn,
        LocalDate returnedOn
) {
}
