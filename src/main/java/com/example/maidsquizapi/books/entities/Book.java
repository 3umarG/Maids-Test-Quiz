package com.example.maidsquizapi.books.entities;

import com.example.maidsquizapi.borrowing.entities.BorrowedBook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@Setter
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String author;

    @Column(name = "published_on", columnDefinition = "DATE")
    private LocalDate publishedOn;

    @Column(length = 20, unique = true)
    private String isbnNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "book")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<BorrowedBook> borrowedBooks = new HashSet<>();

    public Book(String title, String author, LocalDate publishedOn, String isbnNumber) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
        this.isbnNumber = isbnNumber;
    }

    public Book(Integer id, String title, String author, LocalDate publishedOn, String isbnNumber) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
        this.isbnNumber = isbnNumber;
    }
}
