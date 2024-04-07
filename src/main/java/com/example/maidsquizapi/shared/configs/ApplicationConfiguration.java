package com.example.maidsquizapi.shared.configs;

import com.example.maidsquizapi.patrons.DAO.PatronsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final PatronsRepository patronsRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return (String email) -> patronsRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with Email : " + email + ", not found !!"));
    }
}
