package zadanie1.controller;

import zadanie1.model.UtilityCharge;
import zadanie1.service.UtilityChargeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/utility-charges")
public class UtilityChargeController {
    private final UtilityChargeService service;

    public UtilityChargeController(UtilityChargeService service) { this.service = service; }

    @GetMapping
    public List<UtilityCharge> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public UtilityCharge getById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<UtilityCharge> create(@RequestBody UtilityCharge c) {
        UtilityCharge created = service.create(c);
        return ResponseEntity.created(URI.create("/api/utility-charges/" + created.getId())).body(created);
    }

    @PatchMapping("/{id}")
    public UtilityCharge update(@PathVariable Long id, @RequestBody UtilityCharge patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}