package zadanie1.repository;
import zadanie1.model.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MeterRepository extends JpaRepository<Meter, Long> {}