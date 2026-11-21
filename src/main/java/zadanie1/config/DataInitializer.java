package zadanie1.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zadanie1.model.Role;
import zadanie1.model.User;
import zadanie1.repository.UserRepository;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User a = new User();
            a.setUsername("admin");
            a.setPassword(passwordEncoder.encode("Admin1234"));
            a.setRole(Role.ADMIN);
            a.setEnabled(true);
            a.setFullName("Administrator");
            userRepository.save(a);
        }
        if (userRepository.findByUsername("user").isEmpty()) {
            User u = new User();
            u.setUsername("user");
            u.setPassword(passwordEncoder.encode("User1234"));
            u.setRole(Role.USER);
            u.setEnabled(true);
            u.setFullName("Regular User");
            userRepository.save(u);
        }
    }
}