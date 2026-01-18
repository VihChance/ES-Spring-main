package com.example.spring.config;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Docente;
import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRole;
import com.example.spring.repository.AlunoRepository;
import com.example.spring.repository.DocenteRepository;
import com.example.spring.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepo,
            AlunoRepository alunoRepo,
            DocenteRepository docenteRepo
    ) {
        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // ========== ADMIN ==========
            String adminEmail = "admin@estg.pt";
            if (userRepo.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(UserRole.ADMIN);
                userRepo.save(admin);

                System.out.println("Admin criado com sucesso.");
            }


            // ========== DOCENTE ==========
            String docenteEmail = "admin@estg.pt";
            if (userRepo.findByEmail(docenteEmail).isEmpty()) {

                Docente d = new Docente("Prof. Jorge Machado", docenteEmail);
                docenteRepo.save(d);

                User u = new User();
                u.setEmail(docenteEmail);
                u.setPassword(encoder.encode("1234"));
                u.setRole(UserRole.DOCENTE);
                u.setDocente(d);

                userRepo.save(u);

                System.out.println("Docente + User criado com sucesso.");
            }


            // ========== ALUNO ==========
            String alunoEmail = "aluno@estg.pt";
            if (userRepo.findByEmail(alunoEmail).isEmpty()) {

                Aluno a = new Aluno("Maria Estudante", alunoEmail);
                alunoRepo.save(a);

                User u = new User();
                u.setEmail(alunoEmail);
                u.setPassword(encoder.encode("1234"));
                u.setRole(UserRole.ALUNO);
                u.setAluno(a);

                userRepo.save(u);

                System.out.println("Aluno + User criado com sucesso.");
            }
        };
    }
}
