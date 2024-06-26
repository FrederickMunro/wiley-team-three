package wileyt3.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures the web security for the application.
 * It defines how the security is structured, including session management and request authorizations.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    /**
     * Defines the security filter chain configuration.
     * Sets up HTTP security, disabling CSRF for statelessness and configuring session to be stateless.
     * Stateless means that the server does not store session state information in any way. (Each request needs all necessary information)
     * Registers JwtAuthFilter before the BasicAuthenticationFilter to use custom token-based auth.
     *
     * @param http HttpSecurity to configure
     * @return A built SecurityFilterChain after applying the specified configurations.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configureCsrf(http);
        configureSession(http);
        configureAuthorization(http);
        configureFilters(http);
        configureExceptionHandling(http);
        return http.build();
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
    }

    private void configureSession(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Public
                .requestMatchers("/login", "/register", "user-info", "/crypto/**").permitAll()
                //Swagger
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**").permitAll()
                // Stock Operations by ADMIN
                .requestMatchers(HttpMethod.POST, "/stocks/").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/stocks/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/stocks/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/stocks/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/stocks/get-all-api").hasAuthority("ADMIN")
                // Stock Operations for TRADER
                .requestMatchers(HttpMethod.GET, "/trader/**").hasAuthority("TRADER")
                // Portfolio Operations for TRADER
                .requestMatchers("/portfolio/**").hasAuthority("TRADER")
                .requestMatchers("/analyst/**").hasAuthority("ANALYST")
                .anyRequest().authenticated());
    }


    private void configureFilters(HttpSecurity http) {
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private void configureExceptionHandling(HttpSecurity http) throws Exception {
        http.exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
    }

    @Bean
    public JwtAuthFilter jwtAuthenticationFilter() {
        return new JwtAuthFilter(userAuthProvider);
    }
}
