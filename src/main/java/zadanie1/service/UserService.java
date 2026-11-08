package zadanie1.service;

import zadanie1.model.User;
import zadanie1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    public User create(User user) {
        user.setId(null);
        return repository.save(user);
    }

    public User update(Long id, User patch) {
        User existing = findById(id);
        if (patch.getFullName() != null) existing.setFullName(patch.getFullName());
        if (patch.getEmail() != null) existing.setEmail(patch.getEmail());
        if (patch.getPhone() != null) existing.setPhone(patch.getPhone());
        return repository.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}