package com.example.maidsquizapi.patrons.entities;

import com.example.maidsquizapi.borrowing.entities.BorrowedBook;
import com.example.maidsquizapi.patrons.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patrons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patron implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "patron")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<BorrowedBook> borrowedBooks = new HashSet<>();

    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Patron(String name, String phone, String email, String hashedPassword, UserRole role) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public Patron(String name, String phone, String email, String hashedPassword) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = UserRole.ROLE_PATRON;
    }

    public static Patron createManager(String name, String phone, String email, String hashedPassword) {
        return new Patron(
                name,
                phone,
                email,
                hashedPassword,
                UserRole.ROLE_MANAGER
        );
    }

    public static Patron createPatron(String name, String phone, String email, String hashedPassword) {
        return new Patron(
                name,
                phone,
                email,
                hashedPassword,
                UserRole.ROLE_PATRON
        );
    }

}
