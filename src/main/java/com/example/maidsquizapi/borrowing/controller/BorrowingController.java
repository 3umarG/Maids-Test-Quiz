package com.example.maidsquizapi.borrowing.controller;

import com.example.maidsquizapi.borrowing.services.BorrowingService;
import com.example.maidsquizapi.shared.response.ApiCustomResponse;
import com.example.maidsquizapi.shared.response.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("${api.version}")
@RequiredArgsConstructor
@Tag(name = "Borrowing Management")
public class BorrowingController {

    private final BorrowingService service;

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<ApiCustomResponse> borrowBook(@PathVariable Integer bookId) {

        var result = service.borrowBook(bookId);
        return ResponseWrapper
                .created(result)
                .toResponseEntity();
    }

    @PutMapping("/return/{bookId}")
    public ResponseEntity<ApiCustomResponse> returnBook(@PathVariable Integer bookId) {
        var result = service.returnBook(bookId);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();

    }
}
