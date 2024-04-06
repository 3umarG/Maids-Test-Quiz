package com.example.maidsquizapi.shared;

import org.springframework.http.ResponseEntity;


public class ResponseWrapper {
    private final Object data;
    private final String message;
    private final int statusCode;
    private final boolean success;

    private ResponseWrapper(Object data, String message, int statusCode, boolean success) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
        this.success = success;
    }

    public static ResponseWrapper ok(Object data) {
        return new ResponseWrapper(
                data,
                null,
                200,
                true
        );
    }

    public static ResponseWrapper error(String message, int statusCode) {
        return new ResponseWrapper(
                null,
                message,
                statusCode,
                false
        );
    }

    public static ResponseWrapper error(String message, int statusCode, Object data) {
        return new ResponseWrapper(
                data,
                message,
                statusCode,
                false
        );
    }


    public ResponseEntity<ApiCustomResponse> toResponseEntity() {
        ApiCustomResponse customResponse = new ApiCustomResponse(
                message,
                statusCode,
                success,
                data
        );

        return ResponseEntity
                .status(statusCode)
                .body(customResponse);
    }
}

