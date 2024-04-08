package com.example.maidsquizapi.borrowing.services;

import com.example.maidsquizapi.books.DAO.BooksRepository;
import com.example.maidsquizapi.books.entities.Book;
import com.example.maidsquizapi.borrowing.DAO.BorrowedBooksRepository;
import com.example.maidsquizapi.borrowing.DTOs.BorrowedBookResponseDto;
import com.example.maidsquizapi.borrowing.entities.BorrowedBook;
import com.example.maidsquizapi.exceptions.NotFoundCustomException;
import com.example.maidsquizapi.exceptions.NotReturnedBorrowedBookException;
import com.example.maidsquizapi.patrons.entities.Patron;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowedBooksRepository borrowedBooksrepository;
    private final BooksRepository booksService;


    public BorrowedBookResponseDto borrowBook(Integer bookId) {

        var patron = (Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var book = findBookByIdOrThrowNotFound(bookId);

        if (borrowedBooksrepository.existsByPatronIdAndBookIdAndReturnedOnIsNull(patron.getId(), bookId)) {
            throw new NotReturnedBorrowedBookException();
        }

        var borrowedBook = new BorrowedBook(patron, book);
        borrowedBook = borrowedBooksrepository.save(borrowedBook);

        return new BorrowedBookResponseDto(
                patron.getId(),
                patron.getName(),
                bookId,
                book.getTitle(),
                borrowedBook.getBorrowedOn(),
                null
        );
    }

    private Book findBookByIdOrThrowNotFound(Integer bookId) {
        return booksService.findById(bookId).orElseThrow(
                () -> new NotFoundCustomException("Not Found Book with ID : " + bookId)
        );
    }

    public BorrowedBookResponseDto returnBook(Integer bookId) {
        var patron = (Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var book = findBookByIdOrThrowNotFound(bookId);

        var borrowedBook = borrowedBooksrepository.findByPatronIdAndBookIdAndReturnedOnIsNull(patron.getId(), bookId).orElseThrow(
                () -> new NotFoundCustomException("There is no borrowed books with that ID to be returned !!")
        );

        borrowedBook.setReturnedOn(LocalDate.now());
        borrowedBook = borrowedBooksrepository.save(borrowedBook);

        return new BorrowedBookResponseDto(
                patron.getId(),
                patron.getName(),
                bookId,
                book.getTitle(),
                borrowedBook.getBorrowedOn(),
                borrowedBook.getReturnedOn()
        );


    }
}
