package com.example.maidsquizapi.services;

import com.example.maidsquizapi.books.DAO.BooksRepository;
import com.example.maidsquizapi.books.DTOs.BookRequestDto;
import com.example.maidsquizapi.books.entities.Book;
import com.example.maidsquizapi.books.services.BooksService;
import com.example.maidsquizapi.shared.exceptions.AlreadyUsedISBNException;
import com.example.maidsquizapi.shared.exceptions.NotFoundCustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {
    @InjectMocks
    private BooksService booksService;

    @Mock
    private BooksRepository booksRepository;

    @BeforeEach
    public void clean() {
        booksRepository.deleteAll();
    }

    @Test
    public void testGetBooks_shouldReturnAllBooks() {
        List<Book> expectedBooks = Arrays.asList(
                new Book(1, "Title 1", "Author 1", LocalDate.now(), "1234567890"),
                new Book(2, "Title 2", "Author 2", LocalDate.now().minusDays(10), "9876543210"));

        when(booksRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = booksService.getBooks();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testGetBookById_shouldThrowNotFoundException_whenBookNotFound() {
        Integer id = 10;
        when(booksRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundCustomException.class, () -> booksService.getBookById(id));
    }

    @Test
    public void testAddBook_shouldThrowAlreadyUsedISBNException_whenIsbnAlreadyExists() {
        BookRequestDto bookRequestDto = new BookRequestDto("New Title", "New Author", LocalDate.now(), "1234567890");

        when(booksRepository.existsByIsbnNumber(bookRequestDto.isbnNumber())).thenReturn(true);

        assertThrows(AlreadyUsedISBNException.class, () -> booksService.addBook(bookRequestDto));
    }

    @Test
    @Transactional
    public void testUpdateBook_shouldUpdateExistingBook() {
        Integer id = 1;
        Book existingBook = new Book(id, "Old Title", "Old Author", LocalDate.now(), "1234567890");
        BookRequestDto updateDto = new BookRequestDto("Updated Title", "Updated Author", LocalDate.now().plusDays(5), "22222222222");

        when(booksRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(booksRepository.existsByIsbnNumber(updateDto.isbnNumber())).thenReturn(false);
        when(booksRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = booksService.updateBook(id, updateDto);

        assertEquals(updateDto.title(), updatedBook.getTitle());
        assertEquals(updateDto.author(), updatedBook.getAuthor());
        assertEquals(updateDto.isbnNumber(), updatedBook.getIsbnNumber());
        assertEquals(updateDto.publishedOn(), updatedBook.getPublishedOn());
    }

    @Test
    public void testUpdateBook_shouldThrowAlreadyUsedISBNException_whenIsbnAlreadyExists_forDifferentBook() {
        Integer id = 1;
        Book existingBook = new Book(id, "Old Title", "Old Author", LocalDate.now(), "9876543210");
        BookRequestDto updateDto = new BookRequestDto("Updated Title", "Updated Author", LocalDate.now().plusDays(5), "1234567890");

        when(booksRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(booksRepository.existsByIsbnNumber(updateDto.isbnNumber())).thenReturn(true);

        assertThrows(AlreadyUsedISBNException.class, () -> booksService.updateBook(id, updateDto));
    }

    @Test
    public void testUpdateBook_shouldThrowNotFoundException_whenBookNotFound() {
        Integer id = 10;
        BookRequestDto updateDto = new BookRequestDto("Updated Title", "Updated Author", LocalDate.now().plusDays(5), "0000000000");

        when(booksRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundCustomException.class, () -> booksService.updateBook(id, updateDto));
    }

    @Test
    @Transactional
    public void testDeleteBookById_shouldDeleteBook() {
        Integer id = 1;
        Book existingBook = new Book(id, "Old Title", "Old Author", LocalDate.now(), "1234567890");

        when(booksRepository.findById(id)).thenReturn(Optional.of(existingBook));

        booksService.deleteBookById(id);

        verify(booksRepository).delete(existingBook);
    }

    @Test
    public void testDeleteBookById_shouldThrowNotFoundException_whenBookNotFound() {
        Integer id = 10;

        when(booksRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundCustomException.class, () -> booksService.deleteBookById(id));

    }
}
