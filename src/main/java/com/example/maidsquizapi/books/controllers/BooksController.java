package com.example.maidsquizapi.books.controllers;

import com.example.maidsquizapi.books.DTOs.BookRequestDto;
import com.example.maidsquizapi.books.services.BooksService;
import com.example.maidsquizapi.shared.response.ApiCustomResponse;
import com.example.maidsquizapi.shared.response.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/books")
@Tag(name = "Books Management")
@PreAuthorize("hasAnyRole('ROLE_PATRON','ROLE_MANAGER')")
public class BooksController {

    private final BooksService booksService;

    /**
     * ● Book management endpoints:
     * ● GET /api/books: Retrieve a list of all books.
     * ● GET /api/books/{id}: Retrieve details of a specific book by ID.
     * ● POST /api/books: Add a new book to the library.
     * ● PUT /api/books/{id}: Update an existing book's information.
     * ● DELETE /api/books/{id}: Remove a book from the library.
     */

    @GetMapping
    @PreAuthorize("hasAuthority('patron:read')")
    public ResponseEntity<ApiCustomResponse> getBooks() {
        var result = booksService.getBooks();
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('patron:read')")
    public ResponseEntity<ApiCustomResponse> getBookById(@PathVariable Integer id) {
        var result = booksService.getBookById(id);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('management:write')")
    public ResponseEntity<ApiCustomResponse> addBook(@Valid @RequestBody BookRequestDto body) {
        var result = booksService.addBook(body);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('management:write')")
    public ResponseEntity<ApiCustomResponse> updateBookById(@PathVariable Integer id,@Valid @RequestBody BookRequestDto body){
        var result = booksService.updateBook(id,body);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('management:write')")
    public ResponseEntity<ApiCustomResponse> deleteBookById(@PathVariable Integer id) {
        var result = booksService.deleteBookById(id);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }
}
