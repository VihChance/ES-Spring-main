package com.example.spring.config;

import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRole;
import com.example.spring.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository repository) {

        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (repository.findByEmail("admin@estg.pt").isEmpty()) {
                User docente = new User();
                docente.setEmail("admin@estg.pt");
                docente.setPassword(encoder.encode("1234"));
                docente.setRole(UserRole.DOCENTE);
                repository.save(docente);
            }

            if (repository.findByEmail("aluno@estg.pt").isEmpty()) {
                User aluno = new User();
                aluno.setEmail("aluno@estg.pt");
                aluno.setPassword(encoder.encode("1234"));
                aluno.setRole(UserRole.ALUNO);
                repository.save(aluno);
            }
        };
    }
}
