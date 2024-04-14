package com.example.maidsquizapi;

import com.example.maidsquizapi.patrons.DTOs.request.LoginRequestDto;
import com.example.maidsquizapi.patrons.DTOs.request.SignUpRequestDto;
import com.example.maidsquizapi.patrons.enums.UserRole;
import com.example.maidsquizapi.patrons.services.PatronsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@Slf4j
public class MaidsQuizApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaidsQuizApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PatronsService service) {
        return args -> {
            var patron = new SignUpRequestDto(
                    "patron",
                    "01025087698",
                    "patron@gmail.com",
                    "password",
                    UserRole.ROLE_PATRON
            );
            service.register(patron);

            log.info("Patron token : " + service.login(new LoginRequestDto("patron@gmail.com", "password")).accessToken());


            var manager = new SignUpRequestDto(
                    "manager",
                    "01265478963",
                    "manager@gmail.com",
                    "password",
                    UserRole.ROLE_MANAGER
            );
            service.register(manager);

            log.info("Manager Token : " + service.login(new LoginRequestDto("manager@gmail.com", "password")).accessToken());
        };
    }
}
