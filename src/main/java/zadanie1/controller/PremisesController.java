package zadanie1.controller;

import zadanie1.model.Premises;
import zadanie1.service.PremisesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/premises")
public class PremisesController {
    private final PremisesService service;

    public PremisesController(PremisesService service) { this.service = service; }

    @GetMapping
    public List<Premises> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Premises getById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Premises> create(@RequestBody Premises p) {
        Premises created = service.create(p);
        return ResponseEntity.created(URI.create("/api/premises/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}")
    public Premises update(@PathVariable Long id, @RequestBody Premises patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}