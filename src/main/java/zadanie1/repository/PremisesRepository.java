package zadanie1.repository;
import zadanie1.model.Premises;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PremisesRepository extends JpaRepository<Premises, Long> {}