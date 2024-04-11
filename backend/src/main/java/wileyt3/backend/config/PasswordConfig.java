package wileyt3.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Configuration class to define the password encoder bean used throughout the application.
 * BCryptPasswordEncoder is chosen for its strength and ability to hash passwords securely.
 */
@Component
public class PasswordConfig {
    /**
     * Creates a BCryptPasswordEncoder bean to be used for password encoding.
     * BCrypt is a strong and adaptive cryptographic password hashing function.
     *
     * @return A PasswordEncoder instance using BCrypt hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}