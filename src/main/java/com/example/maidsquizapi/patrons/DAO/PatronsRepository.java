package com.example.maidsquizapi.patrons.DAO;

import com.example.maidsquizapi.patrons.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronsRepository extends JpaRepository<Patron, Integer> {

    Optional<Patron> findByEmail(String email);
    Boolean existsByEmailOrPhone(String email,String phone);
}