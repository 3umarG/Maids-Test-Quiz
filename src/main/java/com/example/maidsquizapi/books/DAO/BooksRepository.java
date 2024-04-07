package com.example.maidsquizapi.books.DAO;

import com.example.maidsquizapi.books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Boolean existsByIsbnNumber(String isbnNumber);
}