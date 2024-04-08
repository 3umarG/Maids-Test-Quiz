package com.example.maidsquizapi.shared.exceptions;

public class AlreadyUsedISBNException extends CustomException{
    public AlreadyUsedISBNException(String isbn) {
        super(isbn + " ISBN already used by another book !!", 400);
    }
}
