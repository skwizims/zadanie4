package zadanie1.service;

import zadanie1.model.Meter;
import zadanie1.model.Premises;
import zadanie1.repository.MeterRepository;
import zadanie1.repository.PremisesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeterService {
    private final MeterRepository meterRepo;
    private final PremisesRepository premisesRepo;

    public MeterService(MeterRepository meterRepo, PremisesRepository premisesRepo) {
        this.meterRepo = meterRepo;
        this.premisesRepo = premisesRepo;
    }

    public List<Meter> findAll() {
        return meterRepo.findAll();
    }

    public Meter findById(Long id) {
        return meterRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Meter not found: " + id));
    }

    public Meter create(Meter m) {
        if (m.getPremises() == null || m.getPremises().getId() == null) {
            throw new IllegalArgumentException("Premises is required");
        }
        Premises premises = premisesRepo.findById(m.getPremises().getId())
                .orElseThrow(() -> new IllegalArgumentException("Premises not found"));
        m.setPremises(premises);
        m.setId(null);
        return meterRepo.save(m);
    }

    public Meter update(Long id, Meter patch) {
        Meter existing = findById(id);
        if (patch.getPremises() != null && patch.getPremises().getId() != null) {
            Premises premises = premisesRepo.findById(patch.getPremises().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Premises not found"));
            existing.setPremises(premises);
        }
        if (patch.getResourceType() != null) existing.setResourceType(patch.getResourceType());
        if (patch.getSerialNumber() != null) existing.setSerialNumber(patch.getSerialNumber());
        if (patch.getTariff() != null) existing.setTariff(patch.getTariff());
        if (patch.getStatus() != null) existing.setStatus(patch.getStatus());
        return meterRepo.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        meterRepo.deleteById(id);
    }
}