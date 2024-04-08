package com.example.maidsquizapi.exceptions;

public class NotReturnedBorrowedBookException extends CustomException{
    public NotReturnedBorrowedBookException() {
        super("You have already borrowed this book, and have not returned it yet !!", 400);
    }
}
