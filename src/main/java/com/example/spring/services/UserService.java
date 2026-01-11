package com.example.spring.services;

import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRole;
import com.example.spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // -------- POST --------
    public User criarUser(String email, String password, UserRole role) {

        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já existe");
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(password)); //encriptar pass
        u.setRole(role);

        return repository.save(u);
    }

    // -------- GET ALL --------
    public List<User> listarUsers() {
        return repository.findAll();
    }

    // -------- GET BY ID --------
    public User loadUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    // -------- DELETE --------
    public boolean apagarUser(Long id) {
        User u = repository.findById(id).orElse(null);
        if (u == null) {
            return false;
        }
        repository.delete(u);
        return true;
    }

    // -------- PUT --------
    public User atualizarUser(Long id, String email, UserRole role) {

        User u = repository.findById(id).orElse(null);
        if (u == null) {
            return null;
        }

        var outro = repository.findByEmail(email);
        if (outro.isPresent() && !outro.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email já existe");
        }

        u.setEmail(email);
        u.setRole(role);

        return repository.save(u);
    }

    public boolean login(String email, String password) {

        var userOpt = repository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        return encoder.matches(password, user.getPassword());
    }

}
