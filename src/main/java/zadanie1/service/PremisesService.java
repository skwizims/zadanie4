package zadanie1.service;

import zadanie1.model.Premises;
import zadanie1.model.User;
import zadanie1.repository.PremisesRepository;
import zadanie1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PremisesService {
    private final PremisesRepository premisesRepo;
    private final UserRepository userRepo;

    public PremisesService(PremisesRepository premisesRepo, UserRepository userRepo) {
        this.premisesRepo = premisesRepo;
        this.userRepo = userRepo;
    }

    public List<Premises> findAll() {
        return premisesRepo.findAll();
    }

    public Premises findById(Long id) {
        return premisesRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Premises not found: " + id));
    }

    public Premises create(Premises p) {
        if (p.getOwner() == null || p.getOwner().getId() == null) {
            throw new IllegalArgumentException("Owner is required");
        }
        User owner = userRepo.findById(p.getOwner().getId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        p.setOwner(owner);
        p.setId(null);
        return premisesRepo.save(p);
    }

    public Premises update(Long id, Premises patch) {
        Premises existing = findById(id);
        if (patch.getAddress() != null) existing.setAddress(patch.getAddress());
        if (patch.getArea() != null) existing.setArea(patch.getArea());
        if (patch.getOwner() != null && patch.getOwner().getId() != null) {
            User owner = userRepo.findById(patch.getOwner().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
            existing.setOwner(owner);
        }
        return premisesRepo.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        premisesRepo.deleteById(id);
    }
}