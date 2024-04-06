package com.example.maidsquizapi.shared;

public record ApiCustomResponse(
        String message,
        Integer statusCode,
        Boolean isSuccess,
        Object data
) {
}
