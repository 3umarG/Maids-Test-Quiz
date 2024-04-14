package com.example.maidsquizapi.patrons.controllers;

import com.example.maidsquizapi.patrons.DTOs.request.LoginRequestDto;
import com.example.maidsquizapi.patrons.DTOs.request.SignUpRequestDto;
import com.example.maidsquizapi.patrons.DTOs.request.UpdatePatronRequestDto;
import com.example.maidsquizapi.patrons.services.PatronsService;
import com.example.maidsquizapi.shared.response.ApiCustomResponse;
import com.example.maidsquizapi.shared.response.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/patrons")
@RequiredArgsConstructor
@Tag(name = "Patrons Management")
public class PatronsController {
    private final PatronsService service;


    /**
     * ● Patron management endpoints:
     * ● GET /api/patrons: Retrieve a list of all patrons.
     * ● GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
     * ● POST /api/patrons: Add a new patron to the system.
     * ● PUT /api/patrons/{id}: Update an existing patron's information.
     * ● DELETE /api/patrons/{id}: Remove a patron from the system.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('management:read')")
    public ResponseEntity<ApiCustomResponse> getPatrons() {
        var result = service.getPatrons();
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('management:read')")
    public ResponseEntity<ApiCustomResponse> getPatronById(@PathVariable Integer id) {
        var result = service.getPatronById(id);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiCustomResponse> login(@Valid @RequestBody LoginRequestDto dto) {
        var result = service.login(dto);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiCustomResponse> register(@Valid @RequestBody SignUpRequestDto dto) {
        var result = service.register(dto);
        return ResponseWrapper
                .created(result)
                .toResponseEntity();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('patron:write')")
    public ResponseEntity<ApiCustomResponse> updatePatronInformation(@PathVariable Integer id,
                                                                     @Valid @RequestBody UpdatePatronRequestDto dto) {
        var result = service.updatePatronById(id, dto);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('management:write')")
    public ResponseEntity<ApiCustomResponse> deletePatronInformation(@PathVariable Integer id) {
        var result = service.deletePatronById(id);
        return ResponseWrapper
                .ok(result)
                .toResponseEntity();
    }
}
