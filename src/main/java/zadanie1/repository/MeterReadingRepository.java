package zadanie1.repository;
import zadanie1.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {}