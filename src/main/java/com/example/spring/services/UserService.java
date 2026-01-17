package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Docente;
import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRole;
import com.example.spring.repository.UserRepository;
import com.example.spring.services.exceptions.ConflictException;
import com.example.spring.services.exceptions.NotFoundException;
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

    // -------- POST SIMPLES --------
    public User criarUser(String email, String password, UserRole role) {
        if (repository.findByEmail(email).isPresent()) {
            throw new ConflictException("Email já existe");
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(role);

        return repository.save(u);
    }

    // -------- POST COM LIGAÇÃO --------
    public User criarUserComVinculo(String email, String password, UserRole role, Aluno aluno, Docente docente) {
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

    // -------- GET ALL --------
    public List<User> listarUsers() {
        return repository.findAll();
    }

    // -------- GET BY ID --------
    public User loadUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado"));
    }

    // -------- DELETE --------
    public boolean apagarUser(Long id) {
        User u = loadUser(id);
        repository.delete(u);
        return true;
    }

    // -------- PUT --------
    public User atualizarUser(Long id, String email, UserRole role) {
        User u = loadUser(id);

        var outro = repository.findByEmail(email);
        if (outro.isPresent() && !outro.get().getId().equals(id)) {
            throw new ConflictException("Email já existe");
        }

        u.setEmail(email);
        u.setRole(role);
        return repository.save(u);
    }

    // -------- LOGIN QUE RETORNA USER --------
    public User login(String email, String password) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email inválido"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Senha inválida");
        }

        return user;
    }

    // -------- FIND BY EMAIL --------
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User com email '" + email + "' não encontrado"));
    }
}
