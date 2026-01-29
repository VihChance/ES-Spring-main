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
    CommandLineRunner initData(UserRepository userRepo) {
        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // ========== ADMIN ========== //
            String adminEmail = "admin@estg.pt";
            if (userRepo.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(UserRole.ADMIN);
                userRepo.save(admin);

                System.out.println("Admin criado com sucesso.");
            }
        };
    }
}
