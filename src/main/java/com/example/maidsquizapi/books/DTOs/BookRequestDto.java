package com.example.maidsquizapi.books.DTOs;

import com.example.maidsquizapi.books.entities.Book;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record BookRequestDto(

        @NotNull
        @NotBlank
        String title,

        @NotNull
        @NotBlank
        String author,

        @NotNull
        @PastOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate publishedOn,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
        String isbnNumber
) {
    public Book toBook() {
        return new Book(title, author, publishedOn, isbnNumber);
    }
}
