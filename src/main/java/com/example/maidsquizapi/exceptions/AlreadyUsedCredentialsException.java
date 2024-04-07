package com.example.maidsquizapi.exceptions;

public class AlreadyUsedCredentialsException extends CustomException{
    public AlreadyUsedCredentialsException(String message) {
        super(message, 400);
    }
}
