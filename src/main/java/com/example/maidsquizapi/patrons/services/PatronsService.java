package com.example.maidsquizapi.patrons.services;

import com.example.maidsquizapi.shared.exceptions.AlreadyUsedCredentialsException;
import com.example.maidsquizapi.shared.exceptions.NotFoundCustomException;
import com.example.maidsquizapi.shared.exceptions.NotMatchedPasswordException;
import com.example.maidsquizapi.patrons.DAO.PatronsRepository;
import com.example.maidsquizapi.patrons.DTOs.response.JwtTokenDto;
import com.example.maidsquizapi.patrons.DTOs.request.LoginRequestDto;
import com.example.maidsquizapi.patrons.DTOs.request.SignUpRequestDto;
import com.example.maidsquizapi.patrons.DTOs.request.UpdatePatronRequestDto;
import com.example.maidsquizapi.patrons.DTOs.response.AuthResponseDto;
import com.example.maidsquizapi.patrons.DTOs.response.PatronResponseDto;
import com.example.maidsquizapi.patrons.entities.Patron;
import com.example.maidsquizapi.patrons.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronsService {
    private final PatronsRepository patronsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public List<PatronResponseDto> getPatrons() {
        return patronsRepository.findAll()
                .stream().map(this::convertPatronEntityToPatronResponseDto)
                .toList();
    }

    public PatronResponseDto getPatronById(Integer id) {
        var patron = findPatronByIdOrThrowNotFound(id);
        return convertPatronEntityToPatronResponseDto(patron);
    }

    private Patron findPatronByIdOrThrowNotFound(Integer id) {
        return patronsRepository.findById(id).orElseThrow(() ->
                new NotFoundCustomException("Not Found Patron with ID : " + id));
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        var patron = patronsRepository.findByEmail(dto.email()).
                orElseThrow(() -> new UsernameNotFoundException("User with Email : " + dto.email() + ", not found !!"));

        if (!passwordEncoder.matches(dto.password(), patron.getHashedPassword())) {
            throw new NotMatchedPasswordException();
        }

        var jwtToken = jwtService.generateToken(patron);

        authenticateUser(dto.email(), dto.password());

        return toAuthResponseDto(patron, jwtToken);
    }

    private AuthResponseDto toAuthResponseDto(Patron patron, JwtTokenDto jwtToken) {
        return new AuthResponseDto(
                patron.getId(),
                patron.getName(),
                patron.getPhone(),
                patron.getEmail(),
                jwtToken.token(),
                convertDateToLocalDateTime(jwtToken.expiresOn())
        );
    }

    private LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
    }

    public PatronResponseDto register(SignUpRequestDto dto) {
        if (patronsRepository.existsByEmailOrPhone(dto.email(), dto.phone())) {
            throw new AlreadyUsedCredentialsException("Already used email or phone number ..!!");
        }

        var patron = saveUserToDb((dto));
        return convertPatronEntityToPatronResponseDto(patron);
    }

    private Patron saveUserToDb(SignUpRequestDto dto) {
        var hashedPassword = passwordEncoder.encode(dto.password());
        var patron = new Patron(dto.name(), dto.phone(), dto.email(), hashedPassword);

        patron = patronsRepository.save(patron);

        return patron;
    }

    public PatronResponseDto updatePatronById(Integer id, @Valid UpdatePatronRequestDto dto) {
        var patron = findPatronByIdOrThrowNotFound(id);

        patron.setName(dto.name());
        patron.setPhone(dto.phone());
        patron.setEmail(dto.email());

        patron = patronsRepository.save(patron);
        return convertPatronEntityToPatronResponseDto(patron);
    }

    public PatronResponseDto deletePatronById(Integer id) {
        var patron = findPatronByIdOrThrowNotFound(id);

        patronsRepository.delete(patron);
        return convertPatronEntityToPatronResponseDto(patron);
    }

    private PatronResponseDto convertPatronEntityToPatronResponseDto(Patron p) {
        return new PatronResponseDto(
                p.getId(),
                p.getName(),
                p.getPhone(),
                p.getEmail()
        );
    }
}
