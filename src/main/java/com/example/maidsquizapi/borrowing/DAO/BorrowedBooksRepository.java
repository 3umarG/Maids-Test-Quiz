package com.example.maidsquizapi.borrowing.DAO;

import com.example.maidsquizapi.borrowing.entities.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowedBooksRepository extends JpaRepository<BorrowedBook, Integer> {

    Boolean existsByPatronIdAndBookIdAndReturnedOnIsNull(Integer patronId, Integer bookId);

    Optional<BorrowedBook> findByPatronIdAndBookIdAndReturnedOnIsNull(Integer patronId,Integer bookId);
}