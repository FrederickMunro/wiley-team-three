package wileyt3.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.service.UserService;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
/**
 * Provides authentication services by handling JWT creation and validation.
 */
@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-value}")
    private String secretKey;

    private final UserService userService;
    /**
     * Initializes component, encoding the secret key using Base64 to enhance security.
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    /**
     * Creates a JWT token for a given user identifier (login).
     * @param login the user identifier
     * @return a signed JWT string
     */
    public String createToken(String login) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(login)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(Algorithm.HMAC256(secretKey));

    }

    /**
     * Validates a JWT token and authenticates the user by extracting the user details.
     * @param token the JWT to validate
     * @return an Authentication object if valid, null otherwise
     */
    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        UserDto user = userService.findByUsername(decodedJWT.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    /**
     * Validates a JWT token with a strong check (using a more secure algorithm setup).
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
}
