package zadanie1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "zadanie1.repository")
@EntityScan(basePackages = "zadanie1.model")
public class Zadanie1Application {

	public static void main(String[] args) {
		SpringApplication.run(Zadanie1Application.class, args);
	}
}