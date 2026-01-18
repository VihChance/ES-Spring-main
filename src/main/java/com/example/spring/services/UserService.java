package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Docente;
import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRole;
import com.example.spring.repository.UserRepository;
import com.example.spring.services.exceptions.ConflictException;
import com.example.spring.services.exceptions.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /* ───────────────────────────────
     *  UTIL — garante que é ADMIN
     * ─────────────────────────────── */
    private void checkAdmin() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new SecurityException("Apenas ADMIN pode executar esta operação");
        }
    }

    /* ───────────── CREATE ───────────── */

    public User criarUser(String email, String password, UserRole role) {
        checkAdmin();

        if (repository.findByEmail(email).isPresent()) {
            throw new ConflictException("Email já existe");
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(role);

        return repository.save(u);
    }

    public User criarUserComVinculo(
            String email,
            String password,
            UserRole role,
            Aluno aluno,
            Docente docente
    ) {
        checkAdmin();

        if (repository.findByEmail(email).isPresent()) {
            throw new ConflictException("Email já existe");
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(role);
        u.setAluno(aluno);
        u.setDocente(docente);

        return repository.save(u);
    }

    /* ───────────── READ ───────────── */

    public List<User> listarUsers() {
        checkAdmin();
        return repository.findAll();
    }

    public User loadUser(Long id) {
        checkAdmin();
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado"));
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User com email '" + email + "' não encontrado")
                );
    }

    /* ───────────── UPDATE ───────────── */

    public User atualizarUser(Long id, String email, UserRole role) {
        checkAdmin();

        User u = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado"));

        var outro = repository.findByEmail(email);
        if (outro.isPresent() && !outro.get().getId().equals(id)) {
            throw new ConflictException("Email já existe");
        }

        u.setEmail(email);
        u.setRole(role);
        return repository.save(u);
    }

    /* ───────────── DELETE ───────────── */

    public boolean apagarUser(Long id) {
        checkAdmin();

        User u = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado"));

        // não apagar o último ADMIN
        if (u.getRole() == UserRole.ADMIN &&
                repository.countByRole(UserRole.ADMIN) == 1) {
            throw new ConflictException("Não é possível apagar o único ADMIN");
        }

        repository.delete(u);
        return true;
    }

    /* ───────────── LOGIN ───────────── */

    public User login(String email, String password) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email inválido"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Senha inválida");
        }

        return user;
    }
}
