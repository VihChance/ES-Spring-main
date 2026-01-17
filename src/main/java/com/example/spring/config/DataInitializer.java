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

            // 📚 Criar DOCENTE + USER
            if (userRepo.findByEmail("admin@estg.pt").isEmpty()) {

                Docente d = new Docente("Prof. Jorge Machado", "admin@estg.pt");
                docenteRepo.save(d);

                User u = new User();
                u.setEmail("admin@estg.pt");
                u.setPassword(encoder.encode("1234"));
                u.setRole(UserRole.DOCENTE);
                u.setDocente(d);

                userRepo.save(u);
            }

            // 🎓 Criar ALUNO + USER
            if (userRepo.findByEmail("aluno@estg.pt").isEmpty()) {

                Aluno a = new Aluno("Maria Estudante", "aluno@estg.pt");
                alunoRepo.save(a);

                User u = new User();
                u.setEmail("aluno@estg.pt");
                u.setPassword(encoder.encode("1234"));
                u.setRole(UserRole.ALUNO);
                u.setAluno(a);

                userRepo.save(u);
            }
        };
    }
}
