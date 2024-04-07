package com.example.maidsquizapi.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NotMatchedPasswordException extends AuthenticationException {
    public NotMatchedPasswordException() {
        super("Not correct password ..!!");
    }
}
