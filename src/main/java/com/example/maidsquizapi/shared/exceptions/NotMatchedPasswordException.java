package com.example.maidsquizapi.shared.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NotMatchedPasswordException extends AuthenticationException {
    public NotMatchedPasswordException() {
        super("Not correct password ..!!");
    }
}
