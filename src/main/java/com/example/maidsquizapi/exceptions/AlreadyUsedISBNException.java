package com.example.maidsquizapi.exceptions;

public class AlreadyUsedISBNException extends CustomException{
    public AlreadyUsedISBNException(String isbn) {
        super(isbn + " ISBN already used by another book !!", 400);
    }
}
