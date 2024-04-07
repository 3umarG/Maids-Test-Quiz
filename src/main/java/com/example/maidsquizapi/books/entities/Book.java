package com.example.maidsquizapi.books.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @Column(name = "published_on",columnDefinition = "DATE")
    private LocalDate publishedOn;

    @Column(length = 20,unique = true)
    private String isbnNumber;

    public Book(String title, String author, LocalDate publishedOn, String isbnNumber) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
        this.isbnNumber = isbnNumber;
    }
}
