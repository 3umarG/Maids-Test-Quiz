package com.example.maidsquizapi.books.services;

import com.example.maidsquizapi.books.DAO.BooksRepository;
import com.example.maidsquizapi.books.DTOs.BookRequestDto;
import com.example.maidsquizapi.books.entities.Book;
import com.example.maidsquizapi.shared.exceptions.AlreadyUsedISBNException;
import com.example.maidsquizapi.shared.exceptions.NotFoundCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;

    @Cacheable("books")
    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Integer id) {
        return findBookByIdOrThrowNotFound(id);
    }

    private Book findBookByIdOrThrowNotFound(Integer id) {
        return booksRepository.findById(id).orElseThrow(() -> new NotFoundCustomException("Not found book with ID : " + id));
    }

    @Transactional
    @CacheEvict(value = "books", allEntries = true)
    public Book addBook(BookRequestDto body) {
        if (booksRepository.existsByIsbnNumber(body.isbnNumber())) {
            throw new AlreadyUsedISBNException(body.isbnNumber());
        }

        var bookEntity = body.toBook();
        bookEntity = booksRepository.save(bookEntity);

        return bookEntity;
    }

    @Transactional
    @CacheEvict(value = "books", allEntries = true)
    public Book updateBook(Integer id, BookRequestDto body) {
        var book = findBookByIdOrThrowNotFound(id);

        if (booksRepository.existsByIsbnNumber(body.isbnNumber())) {
            throw new AlreadyUsedISBNException(body.isbnNumber());
        }

        book.setAuthor(body.author());
        book.setTitle(body.title());
        book.setIsbnNumber(body.isbnNumber());
        book.setPublishedOn(body.publishedOn());

        book = booksRepository.save(book);

        return book;
    }

    @Transactional
    @CacheEvict(value = "books", allEntries = true)
    public Book deleteBookById(Integer id) {
        var book = findBookByIdOrThrowNotFound(id);

        booksRepository.delete(book);

        return book;
    }
}
