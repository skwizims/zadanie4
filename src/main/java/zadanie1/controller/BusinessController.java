package zadanie1.controller;

import zadanie1.service.BusinessService;
import org.springframework.web.bind.annotation.*;
import zadanie1.model.MeterReading;
import zadanie1.model.Premises;
import zadanie1.model.UtilityCharge;

import java.util.Map;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    // 1. Подать показание (передаем meterId в URL, а данные в теле)
    @PostMapping("/meters/{meterId}/readings")
    public MeterReading submitReading(@PathVariable Long meterId, @RequestBody Map<String, Object> body) {
        String period = (String) body.get("period");
        Double value = Double.valueOf(body.get("value").toString());
        return businessService.submitReading(meterId, period, value);
    }

    // 2. Подтвердить показание и создать начисление
    @PostMapping("/readings/{readingId}/approve")
    public UtilityCharge approveReading(@PathVariable Long readingId) {
        return businessService.approveReadingAndCharge(readingId);
    }

    // 3. Оплатить начисление
    @PostMapping("/charges/{chargeId}/pay")
    public UtilityCharge payCharge(@PathVariable Long chargeId) {
        return businessService.payCharge(chargeId);
    }

    // 4. Передать помещение
    @PostMapping("/premises/{premisesId}/transfer/{newOwnerId}")
    public Premises transferPremises(@PathVariable Long premisesId, @PathVariable Long newOwnerId) {
        return businessService.transferPremises(premisesId, newOwnerId);
    }

    // 5. Получить отчёт за период
    @GetMapping("/reports/{period}")
    public Map<String, Object> getReport(@PathVariable String period) {
        return businessService.getConsumptionReport(period);
    }
}