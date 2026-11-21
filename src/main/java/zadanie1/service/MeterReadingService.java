package zadanie1.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import zadanie1.model.MeterReading;
import zadanie1.repository.MeterReadingRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeterReadingService {
    private final MeterReadingRepository repository;

    public MeterReadingService(MeterReadingRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<MeterReading> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or @securityChecks.isReadingOwner(authentication.name, #id)")
    public MeterReading findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MeterReading not found: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MeterReading create(MeterReading r) {
        r.setId(null);
        return repository.save(r);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MeterReading update(Long id, MeterReading patch) {
        MeterReading existing = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MeterReading not found: " + id));
        if (patch.getPeriod() != null) existing.setPeriod(patch.getPeriod());
        if (patch.getValue() != null) existing.setValue(patch.getValue());
        if (patch.getVerificationStatus() != null) existing.setVerificationStatus(patch.getVerificationStatus());
        return repository.save(existing);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MeterReading not found: " + id));
        repository.deleteById(id);
    }
}