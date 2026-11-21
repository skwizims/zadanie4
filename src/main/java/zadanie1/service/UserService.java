package zadanie1.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import zadanie1.model.User;
import zadanie1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or @securityChecks.isSelf(authentication.name, #id)")
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User create(User user) {
        user.setId(null);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityChecks.isSelf(authentication.name, #id)")
    public User update(Long id, User patch) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
        if (patch.getFullName() != null) existing.setFullName(patch.getFullName());
        if (patch.getEmail() != null) existing.setEmail(patch.getEmail());
        if (patch.getPhone() != null) existing.setPhone(patch.getPhone());
        return repository.save(existing);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
        repository.deleteById(id);
    }
}