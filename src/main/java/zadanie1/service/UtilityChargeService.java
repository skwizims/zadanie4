package zadanie1.service;

import zadanie1.model.Meter;
import zadanie1.model.Premises;
import zadanie1.model.UtilityCharge;
import zadanie1.repository.MeterRepository;
import zadanie1.repository.PremisesRepository;
import zadanie1.repository.UtilityChargeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UtilityChargeService {
    private final UtilityChargeRepository chargeRepo;
    private final PremisesRepository premisesRepo;
    private final MeterRepository meterRepo;

    public UtilityChargeService(UtilityChargeRepository chargeRepo, PremisesRepository premisesRepo, MeterRepository meterRepo) {
        this.chargeRepo = chargeRepo;
        this.premisesRepo = premisesRepo;
        this.meterRepo = meterRepo;
    }

    public List<UtilityCharge> findAll() {
        return chargeRepo.findAll();
    }

    public UtilityCharge findById(Long id) {
        return chargeRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UtilityCharge not found: " + id));
    }

    public UtilityCharge create(UtilityCharge c) {
        if (c.getPremises() == null || c.getPremises().getId() == null) {
            throw new IllegalArgumentException("Premises is required");
        }
        if (c.getMeter() != null && c.getMeter().getId() != null) {
            Meter meter = meterRepo.findById(c.getMeter().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Meter not found"));
            c.setMeter(meter);
        }
        Premises premises = premisesRepo.findById(c.getPremises().getId())
                .orElseThrow(() -> new IllegalArgumentException("Premises not found"));
        c.setPremises(premises);
        c.setId(null);
        return chargeRepo.save(c);
    }

    public UtilityCharge update(Long id, UtilityCharge patch) {
        UtilityCharge existing = findById(id);
        if (patch.getPremises() != null && patch.getPremises().getId() != null) {
            Premises premises = premisesRepo.findById(patch.getPremises().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Premises not found"));
            existing.setPremises(premises);
        }
        if (patch.getMeter() != null && patch.getMeter().getId() != null) {
            Meter meter = meterRepo.findById(patch.getMeter().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Meter not found"));
            existing.setMeter(meter);
        }
        if (patch.getPeriod() != null) existing.setPeriod(patch.getPeriod());
        if (patch.getVolume() != null) existing.setVolume(patch.getVolume());
        if (patch.getAmount() != null) existing.setAmount(patch.getAmount());
        if (patch.getStatus() != null) existing.setStatus(patch.getStatus());
        return chargeRepo.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        chargeRepo.deleteById(id);
    }
}