package zadanie1.service;

import zadanie1.model.Meter;
import zadanie1.model.MeterReading;
import zadanie1.repository.MeterReadingRepository;
import zadanie1.repository.MeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeterReadingService {
    private final MeterReadingRepository readingRepo;
    private final MeterRepository meterRepo;

    public MeterReadingService(MeterReadingRepository readingRepo, MeterRepository meterRepo) {
        this.readingRepo = readingRepo;
        this.meterRepo = meterRepo;
    }

    public List<MeterReading> findAll() {
        return readingRepo.findAll();
    }

    public MeterReading findById(Long id) {
        return readingRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MeterReading not found: " + id));
    }

    public MeterReading create(MeterReading r) {
        if (r.getMeter() == null || r.getMeter().getId() == null) {
            throw new IllegalArgumentException("Meter is required");
        }
        Meter meter = meterRepo.findById(r.getMeter().getId())
                .orElseThrow(() -> new IllegalArgumentException("Meter not found"));
        r.setMeter(meter);
        r.setId(null);
        return readingRepo.save(r);
    }

    public MeterReading update(Long id, MeterReading patch) {
        MeterReading existing = findById(id);
        if (patch.getMeter() != null && patch.getMeter().getId() != null) {
            Meter meter = meterRepo.findById(patch.getMeter().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Meter not found"));
            existing.setMeter(meter);
        }
        if (patch.getPeriod() != null) existing.setPeriod(patch.getPeriod());
        if (patch.getValue() != null) existing.setValue(patch.getValue());
        if (patch.getVerificationStatus() != null) existing.setVerificationStatus(patch.getVerificationStatus());
        return readingRepo.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        readingRepo.deleteById(id);
    }
}