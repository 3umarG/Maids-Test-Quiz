package com.example.maidsquizapi.shared.exceptions;

public class NotFoundAuthorizationTokenException extends CustomException {
    public NotFoundAuthorizationTokenException() {
        super("You are UN_AUTHORIZED of accessing this resource!!", 401);
    }
}
