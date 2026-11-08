package zadanie1.controller;

import zadanie1.model.User;
import zadanie1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    @GetMapping
    public List<User> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = service.create(user);
        return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}