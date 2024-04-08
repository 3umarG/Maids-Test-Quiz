package com.example.maidsquizapi.borrowing.entities;

import com.example.maidsquizapi.books.entities.Book;
import com.example.maidsquizapi.patrons.entities.Patron;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "borrowed_books")
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrowed_on", columnDefinition = "DATE")
    private LocalDate borrowedOn;

    @Column(name = "returned_on", columnDefinition = "DATE")
    private LocalDate returnedOn;


    public BorrowedBook(Patron patron, Book book, LocalDate returnedOn) {
        this.patron = patron;
        this.book = book;
        this.borrowedOn = LocalDate.now();
        this.returnedOn = returnedOn;
    }

    public BorrowedBook(Patron patron, Book book) {
        this.patron = patron;
        this.book = book;
        this.borrowedOn = LocalDate.now();
    }
}
