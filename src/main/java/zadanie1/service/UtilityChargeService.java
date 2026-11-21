package zadanie1.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import zadanie1.model.UtilityCharge;
import zadanie1.repository.UtilityChargeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UtilityChargeService {
    private final UtilityChargeRepository repository;

    public UtilityChargeService(UtilityChargeRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UtilityCharge> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or @securityChecks.isChargeOwner(authentication.name, #id)")
    public UtilityCharge findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UtilityCharge not found: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UtilityCharge create(UtilityCharge c) {
        c.setId(null);
        return repository.save(c);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UtilityCharge update(Long id, UtilityCharge patch) {
        UtilityCharge existing = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UtilityCharge not found: " + id));
        if (patch.getPeriod() != null) existing.setPeriod(patch.getPeriod());
        if (patch.getVolume() != null) existing.setVolume(patch.getVolume());
        if (patch.getAmount() != null) existing.setAmount(patch.getAmount());
        if (patch.getStatus() != null) existing.setStatus(patch.getStatus());
        return repository.save(existing);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UtilityCharge not found: " + id));
        repository.deleteById(id);
    }
}