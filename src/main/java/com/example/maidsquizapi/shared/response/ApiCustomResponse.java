package com.example.maidsquizapi.shared.response;

public record ApiCustomResponse(
        String message,
        Integer statusCode,
        Boolean isSuccess,
        Object data
) {
}
