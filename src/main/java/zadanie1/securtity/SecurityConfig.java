package zadanie1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/auth/register", "/api/auth/csrf")
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/csrf").permitAll()

                        // Регистрация пользователей через общий CRUD запрещена
                        .requestMatchers(HttpMethod.POST, "/api/users").denyAll()

                        // Запись в справочники — только ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/users/**", "/api/meters/**", "/api/premises/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/**", "/api/meters/**", "/api/premises/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**", "/api/meters/**", "/api/premises/**").hasRole("ADMIN")

                        // Бизнес-операции (грубая рамка, точная — в @PreAuthorize)
                        .requestMatchers(HttpMethod.POST, "/api/business/meters/*/readings").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/business/readings/*/approve").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/business/charges/*/pay").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/business/premises/*/transfer/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/business/reports/*").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(new RestAccessDeniedHandler())
                );

        return http.build();
    }
}