package zadanie1.controller;

import zadanie1.model.Meter;
import zadanie1.service.MeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/meters")
public class MeterController {
    private final MeterService service;

    public MeterController(MeterService service) { this.service = service; }

    @GetMapping
    public List<Meter> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Meter getById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Meter> create(@RequestBody Meter m) {
        Meter created = service.create(m);
        return ResponseEntity.created(URI.create("/api/meters/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}")
    public Meter update(@PathVariable Long id, @RequestBody Meter patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}