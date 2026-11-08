package zadanie1.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zadanie1.model.*;
import zadanie1.repository.*;

import java.util.NoSuchElementException;

@Service
public class BusinessService {
    private final MeterRepository meterRepo;
    private final MeterReadingRepository readingRepo;
    private final UtilityChargeRepository chargeRepo;
    private final PremisesRepository premisesRepo;
    private final UserRepository userRepo;

    public BusinessService(MeterRepository meterRepo, MeterReadingRepository readingRepo,
                           UtilityChargeRepository chargeRepo, PremisesRepository premisesRepo, UserRepository userRepo) {
        this.meterRepo = meterRepo;
        this.readingRepo = readingRepo;
        this.chargeRepo = chargeRepo;
        this.premisesRepo = premisesRepo;
        this.userRepo = userRepo;
    }

    // 1. Подать показание счётчика (проверяет, что счётчик активен, и значение больше предыдущего)
    @Transactional
    public MeterReading submitReading(Long meterId, String period, Double value) {
        Meter meter = meterRepo.findById(meterId)
                .orElseThrow(() -> new NoSuchElementException("Meter not found"));

        if (!"ACTIVE".equals(meter.getStatus())) {
            throw new IllegalArgumentException("Cannot submit reading for inactive meter");
        }

        // Проверка, что новое значение больше предыдущего
        readingRepo.findAll().stream()
                .filter(r -> r.getMeter().getId().equals(meterId))
                .reduce((first, second) -> second) // берем последнее
                .ifPresent(lastReading -> {
                    if (value < lastReading.getValue()) {
                        throw new IllegalArgumentException("New reading cannot be less than previous (" + lastReading.getValue() + ")");
                    }
                });

        MeterReading reading = new MeterReading();
        reading.setMeter(meter);
        reading.setPeriod(period);
        reading.setValue(value);
        reading.setVerificationStatus("PENDING");
        return readingRepo.save(reading);
    }

    // 2. Подтвердить показание и сформировать начисление
    @Transactional
    public UtilityCharge approveReadingAndCharge(Long readingId) {
        MeterReading reading = readingRepo.findById(readingId)
                .orElseThrow(() -> new NoSuchElementException("Reading not found"));

        if (!"PENDING".equals(reading.getVerificationStatus())) {
            throw new IllegalArgumentException("Reading is already processed");
        }

        reading.setVerificationStatus("APPROVED");
        readingRepo.save(reading);

        Meter meter = reading.getMeter();
        UtilityCharge charge = new UtilityCharge();
        charge.setPremises(meter.getPremises());
        charge.setMeter(meter);
        charge.setPeriod(reading.getPeriod());
        charge.setVolume(reading.getValue());
        // Сумма = объем * тариф
        charge.setAmount(reading.getValue() * meter.getTariff());
        charge.setStatus("UNPAID");

        return chargeRepo.save(charge);
    }

    // 3. Оплатить начисление
    @Transactional
    public UtilityCharge payCharge(Long chargeId) {
        UtilityCharge charge = chargeRepo.findById(chargeId)
                .orElseThrow(() -> new NoSuchElementException("Charge not found"));

        if ("PAID".equals(charge.getStatus())) {
            throw new IllegalArgumentException("Charge is already paid");
        }

        charge.setStatus("PAID");
        return chargeRepo.save(charge);
    }

    // 4. Передать помещение новому владельцу
    @Transactional
    public Premises transferPremises(Long premisesId, Long newOwnerId) {
        Premises premises = premisesRepo.findById(premisesId)
                .orElseThrow(() -> new NoSuchElementException("Premises not found"));
        User newOwner = userRepo.findById(newOwnerId)
                .orElseThrow(() -> new NoSuchElementException("New owner not found"));

        if (premises.getOwner().getId().equals(newOwnerId)) {
            throw new IllegalArgumentException("Premises already belongs to this user");
        }

        premises.setOwner(newOwner);
        return premisesRepo.save(premises);
    }
    // 5. Отчёт по потреблению за период (возвращает Map с итогами)
    public java.util.Map<String, Object> getConsumptionReport(String period) {
        var allCharges = chargeRepo.findAll().stream()
                .filter(c -> c.getPeriod().equals(period))
                .toList();

        if (allCharges.isEmpty()) {
            throw new IllegalArgumentException("No data for period: " + period);
        }

        double totalVolume = allCharges.stream().mapToDouble(UtilityCharge::getVolume).sum();
        double totalAmount = allCharges.stream().mapToDouble(UtilityCharge::getAmount).sum();
        long paidCount = allCharges.stream().filter(c -> "PAID".equals(c.getStatus())).count();

        return java.util.Map.of(
                "period", period,
                "totalVolume", totalVolume,
                "totalAmount", totalAmount,
                "totalCharges", allCharges.size(),
                "paidCharges", paidCount
        );
    }
}