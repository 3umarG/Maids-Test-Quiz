package com.example.maidsquizapi.exceptions;

public class NotFoundCustomException extends CustomException{
    public NotFoundCustomException(String message) {
        super(message, 404);
    }
}
