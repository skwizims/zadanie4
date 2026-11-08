package zadanie1.controller;

import zadanie1.model.MeterReading;
import zadanie1.service.MeterReadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/meter-readings")
public class MeterReadingController {
    private final MeterReadingService service;

    public MeterReadingController(MeterReadingService service) { this.service = service; }

    @GetMapping
    public List<MeterReading> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public MeterReading getById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<MeterReading> create(@RequestBody MeterReading r) {
        MeterReading created = service.create(r);
        return ResponseEntity.created(URI.create("/api/meter-readings/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}")
    public MeterReading update(@PathVariable Long id, @RequestBody MeterReading patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}