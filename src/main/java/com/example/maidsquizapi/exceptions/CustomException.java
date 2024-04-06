package com.example.maidsquizapi.exceptions;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    private final Integer statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
