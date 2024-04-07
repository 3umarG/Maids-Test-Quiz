package com.example.maidsquizapi.books.services;

import com.example.maidsquizapi.books.DAO.BooksRepository;
import com.example.maidsquizapi.books.DTOs.BookRequestDto;
import com.example.maidsquizapi.books.entities.Book;
import com.example.maidsquizapi.exceptions.AlreadyUsedISBNException;
import com.example.maidsquizapi.exceptions.NotFoundCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;


    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return findBookByIdOrThrowNotFound(id);
    }

    private Book findBookByIdOrThrowNotFound(Integer id) {
        return booksRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("Not found book with ID : " + id));
    }

    public Book addBook(BookRequestDto body) {
        if (booksRepository.existsByIsbnNumber(body.isbnNumber())){
            throw new AlreadyUsedISBNException(body.isbnNumber());
        }

        var bookEntity = body.toBook();
        bookEntity = booksRepository.save(bookEntity);
        return bookEntity;
    }

    public Book updateBook(Integer id, BookRequestDto body) {
        var book = findBookByIdOrThrowNotFound(id);

        if (booksRepository.existsByIsbnNumber(body.isbnNumber())){
            throw new AlreadyUsedISBNException(body.isbnNumber());
        }

        book.setAuthor(body.author());
        book.setTitle(body.title());
        book.setIsbnNumber(body.isbnNumber());
        book.setPublishedOn(body.publishedOn());

        book = booksRepository.save(book);

        return book;
    }

    public Book deleteBookById(Integer id) {
        var book = findBookByIdOrThrowNotFound(id);

        booksRepository.delete(book);

        return book;
    }
}