package com.example.maidsquizapi.shared.exceptions;

public class AlreadyUsedCredentialsException extends CustomException{
    public AlreadyUsedCredentialsException(String message) {
        super(message, 400);
    }
}
