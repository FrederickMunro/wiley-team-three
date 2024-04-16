package wileyt3.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.entity.User;
import wileyt3.backend.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides authentication services by handling JWT creation and validation.
 */
@RequiredArgsConstructor
@Component
public class UserAuthProvider implements AuthenticationProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final UserService userService;
    /**
     * Initializes component, encoding the secret key using Base64 to enhance security.
     */
    @PostConstruct
    protected void init() {
       // secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    /**
     * Creates a JWT token for a given user identifier (login).
     *
     * @param username the user identifier
     * @return a signed JWT string
     */
    public String createToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3_600_000); // 1 hour expiration

        return JWT.create()
                .withSubject(username)
                .withArrayClaim("roles", roles.toArray(new String[0]))
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(Algorithm.HMAC256(secretKey));
    }


    /**
     * Validates a JWT token and authenticates the user by extracting the user details.
     *
     * @param token the JWT to validate
     * @return an Authentication object if valid, null otherwise
     */
    public Authentication validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            // Extract username
            String username = decodedJWT.getSubject();
            if (username == null) {
                throw new UsernameNotFoundException("Token does not contain a subject");
            }

            // Extract roles safely
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            if (roles == null) {
                roles = Collections.emptyList(); // Default to empty list if no roles claim is present
            }

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            System.out.println("Authorities: " + authorities);  // Debug log

            // Create authentication token
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Invalid token: ", e);
        }
    }


    /**
     * Validates a JWT token with a strong check (using a more secure algorithm setup).
     *
     * @param token the JWT to validate
     * @return an Authentication object if valid, null otherwise
     */
    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByUsername(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.authenticateUser(username, password);

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getAuthority());

        // Return the authentication token
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                Collections.singletonList(authority) // Wrap single authority in a list
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
